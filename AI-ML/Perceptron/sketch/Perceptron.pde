/*
 * A Perceptron
 */
class Perceptron {
   /** Input weights we keep track of */
  float[] weights;
  /** The learning rate */
  float c;
  
  /** Init a Perceptron with random weights */
  Perceptron(int n, float c) {
    weights = new float[n];
    for(int i = 0; i < weights.length; i++) {
      weights[i] = random(-1, 1);
    }
    this.c = c;
  }
  
  /** Train the perceptron */
  void train(float[] inputs, int desired) {
    // We guess
    int guess = this.feedForward(inputs);
    // We fail
    float loss = desired - guess;
    // We adapt
    for(int i = 0; i < weights.length; i++) {
      weights[i] += c * loss * inputs[i];
    }
  }
  
  /** Takes the inputs and give an output */
  int feedForward(float[] inputs) {
    float sum = 0;
    for(int i = 0; i < weights.length; i++) {
      sum += inputs[i]*weights[i];
    }
    return activate(sum);
  }
  
  // ------------------------
  // Helpers
  
  int activate(float activation) {
    if (activation > 0) return 1;
    else return -1;
  }
  
  String toString() {
    String s = new String();
    s += "== PERCEPTRON ==\n";
    for(int i = 0; i < weights.length; i++) {
      s += "weight " + i + ": " + weights[i] + "\n";
    }
    return s;
  }
}
