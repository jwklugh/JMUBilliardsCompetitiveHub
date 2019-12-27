FileIO io;
Players ps;
Match m;
static final String SETTINGS_LOCATION = "ScoreboardSettings.txt";
                    

int i1 = 0, // Defender   Increment ......... q
    d1 = 0, // Defender   Decrement ......... Q
    i2 = 0, // Challenger Increment ......... p
    d2 = 0, // Challenger Decrement ......... P
    sb = 0, // Swap Break ................... B
    te = 0, // Time Extention  Toggle........ T
    le = 0, // Time Limit Extention Toggle .. L
    re = 0, // Race to Extention Toggle ..... R
    ce = 0, // Score cap Extention Toggle ... C
    ss = 0, // Start/Stop Timer ............. S
    tf = 0; // Toggle Final Display ......... F
PImage backgroundImg, finalImg;
boolean saved;

final int fps = 10;
final boolean HIDDEN = false, VISIBLE = true;

Extention timeExt, raceExt, limiExt, sCapExt, finaExt;

void setup() {
  frameRate(fps);
  size(777,301);
  backgroundImg = loadImage("background0.png");
  finalImg      = loadImage("backgroundf.png");
  setupMatchFromIO();
  timeExt = new Extention(150,229,BOTTOM,VISIBLE);
  raceExt = new Extention(406,229,BOTTOM,VISIBLE);
  limiExt = new Extention(150,10,TOP,HIDDEN);
  sCapExt = new Extention(406,10,TOP,HIDDEN);
  finaExt = new Extention(90,10,TOP,HIDDEN);
}

void draw() {
  background(0,255,0);noTint();
  timeExt.draw(m.t.toString(),34);
  raceExt.draw(m.raceStr(),34);
  limiExt.draw(m.limitStr(),20);
  sCapExt.draw(m.capStr(),23);
  finaExt.draw("Final",40);
  if(backgroundImg != null) {
    image(backgroundImg,0,60,width,181);
    tint(255,255*finalTransition/10);
    image(finalImg,130,241);
  }
  m.draw();
  m.t.updateTime();
  checkSave();
  doFinal();
  //debug();
}

void keyPressed() {
  switch(key) {
    case 'q' : { if(i1<1) {scoreChange(true ,true );  i1++;} break;}
    case 'Q' : { if(d1<1) {scoreChange(false,true );  d1++;} break;}
    case 'p' : { if(i2<1) {scoreChange(true ,false);  i2++;} break;}
    case 'P' : { if(d2<1) {scoreChange(false,false);  d2++;} break;}
    case 'T' : { if(te<1) {timeExt.toggle();          te++;} break;}
    case 'R' : { if(re<1) {raceExt.toggle();          re++;} break;}
    case 'L' : { if(le<1) {limiExt.toggle();          le++;} break;}
    case 'C' : { if(ce<1) {sCapExt.toggle();          ce++;} break;}
    case 'b' : { if(sb<1) {sB();                      sb++;} break;}
    case 'S' : { if(ss<1) {m.t.toggle();              ss++;} break;}
    case 'F' : { if(tf<1) {toggleFinalDisplay();      tf++;} break;}
  }
}

void keyReleased() {
  switch(key) {
    case 'q' : { i1=0; break;}
    case 'Q' : { d1=0; break;}
    case 'p' : { i2=0; break;}
    case 'P' : { d2=0; break;}
    case 'T' : { te=0; break;}
    case 'R' : { re=0; break;}
    case 'L' : { le=0; break;}
    case 'C' : { ce=0; break;}
    case 'b' : { sb=0; break;}
    case 'S' : { ss=0; break;}
    case 'F' : { tf=0; break;}
  }
}

void scoreChange(boolean incriment, boolean defender) {
  if(incriment)
    if(defender)
      m.p.d.s++;
    else
      m.p.c.s++;
  else
    if(defender)
      m.p.d.s--;
    else
      m.p.c.s--;
  sB();
  io.save(m.toString());
}

void sB() { //Short Swap Break Method
  m.defBreak = !m.defBreak;
  io.save(m.toString());
}


void setupMatchFromIO() {
  try {
    //Construct a fileIO from the settings file (edited by the launcher)
    io = new FileIO(SETTINGS_LOCATION);
    
    //Construct an info struct from the file contained in settings
    InfoStruct info = io.getInFile();
    
    //Build the players from the info struct
    ps = new Players(info.dn(),info.dr(), info.cs()
                    ,info.cn(),info.cr(), info.ds());
                    
    //Build the match from the info struct
    m  = new Match(ps, info.rt(),info.sc(),
                       info.tl()*1000,info.ct()*1000,info.db());
    saved = false;
  } catch (IOException e) {
    System.out.println("Error in file IO: \n" + SETTINGS_LOCATION +"\n" + e);
    System.exit(0);
  }
}

void checkSave() {
  //For the first second every 30 seconds, if you haven't already saved, do so
  if(m.t.getMillis() % 30000 <= 1000 && !saved) {
    saved = io.save(m.toString());
  } else if(1000 < m.t.getMillis() %30000) {
    saved = false;
  }
}

int finalTransition = 0;
boolean finalActive = false;
void toggleFinalDisplay() {
  if(timeExt.out) timeExt.toggle();
  if(raceExt.out) raceExt.toggle();
  if(limiExt.out) limiExt.toggle();
  if(sCapExt.out) sCapExt.toggle();
  finalActive = !finalActive;
  finaExt.out = finalActive;
}

void doFinal() {
  if(finalActive && finalTransition<10) finalTransition++;
  if(!finalActive && finalTransition>0) finalTransition--;
}


void debug() {
  //cursor pointers for drawing placement and other information
  textSize(10);
  textAlign(LEFT);
  String[] info = { ""+mouseX , ""+mouseY};
  for(int i=0; i<info.length; i++) {
    text(""+info[i],3,10+i*11);
  }
}
