// Modelling of the Perceptron
// Rosenblatt, Frank. The perceptron, a perceiving and recognizing automaton Project Para. Cornell Aeronautical Laboratory, 1957.
// Florent Dufour - 2018

final int FRAME_RATE = 6;
final int TRAINING_SIZE = 3000;
// The Perceptron
Perceptron p;
// Training set
Trainer[] training = new Trainer[TRAINING_SIZE];
int count = 0;
// Coordinate space
float xmin = -400;
float ymin = -100;
float xmax =  400;
float ymax =  100;
// The linear function: y = 2x + 1
float f(float x) {
  return 0.5 * x + 20;
}

void setup() {
  // Setup visual stuff
  size(640, 360);
  frameRate(FRAME_RATE);
  
  // Setup the Perceptron
  p = new Perceptron(3, 0.0001); // 2D + Bias, low learning rate
    
  // Setup training points
  for (int i = 0; i < training.length; i++) {
    float x = random(xmin, xmax);
    float y = random(ymin, ymax);
    int answer = 1;
    if (y < f(x)) answer = -1;
    training[i] = new Trainer(x, y, answer);
  }
}

void draw() {
  background(255);
  // Translate for humans
  translate(width/2, height/2);
  // Draw the f(x) line
  strokeWeight(4);
  stroke(127);
  float x1 = xmin;
  float y1 = f(x1);
  float x2 = xmax;
  float y2 = f(x2);
  line(x1,y1,x2,y2);
  // Draw the line based on the current weights
  // Formula is weights[0]*x + weights[1]*y + weights[2] = 0
  stroke(0);
  strokeWeight(1);
  float[] weights = p.weights;
  x1 = xmin;
  y1 = (-weights[2] - weights[0]*x1)/weights[1];
  x2 = xmax;
  y2 = (-weights[2] - weights[0]*x2)/weights[1];
  line(x1,y1,x2,y2);
  
  // Train: 1 data point = 1 frame
  p.train(training[count].inputs, training[count].answer);
  count = (count + 1);
  
  // Draw all the points
  for (int i = 0; i < count; i++) {
    stroke(0);
    strokeWeight(1);
    fill(255, 0, 0);
    int guess = p.feedForward(training[i].inputs);
    if (guess > 0) fill(0, 255, 0);
    ellipse(training[i].inputs[0], training[i].inputs[1], 8, 8);
  }
  println("\r > " + count);
  if(count == TRAINING_SIZE) stop();
}
