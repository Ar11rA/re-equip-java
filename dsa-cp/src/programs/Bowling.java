package programs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/*
Bowling is a game where players roll a heavy ball to knock down pins arranged in a triangle.
Write code to keep track of the score of a game of bowling.

The game consists of 10 frames.
A frame is composed of one or two ball throws with 10 pins standing at frame initialization.
A frame has a maximum of 2 throws
There are three cases for the scoring of a frame.

* CASE 1: An open frame is where a score of less than 10 is recorded for the frame. In this case the score for the frame is the total number of pins knocked down.
* CASE 2: A spare is where all ten pins are knocked down by the second throw. The total value of a spare is 10 plus the number of pins knocked down in their next throw.
* CASE 3: A strike is where all ten pins are knocked down by the first throw. The total value of a strike is 10 plus the number of pins knocked down in the next two throws. If a strike is immediately followed by a second strike, then the value of the first strike cannot be determined until the ball is thrown one more time.

e.g. Here is a three frame game:
Frame 1 : X (strike)
Frame 2 : 5/ (spare)
Frame 3 : 9 0 (open frame)

Frame 1 Score calculation is (10 + 5 + 5) = 20 --> CASE 1
Frame 2 Score calculation is (5 + 5 + 9) = 19  --> CASE 2
Frame 3 Score calculation is (9 + 0) = 9       --> CASE 3

Input would look like 10, 5, 5, 9, 0. And this means the current running total is 48.

Note that a real game will be a 10 frame game.

The tenth frame in the game is a special case. If someone throws a strike or a spare then they get a fill ball.
Fill balls exist to calculate the total of the 10th frame. Scoring a strike or spare on the fill ball does not give the player more fill balls
The total value of the 10th frame is the total number of pins knocked down.

e.g.
For a tenth frame of X1/ (strike and a spare), the total value is 20.
For a tenth frame of XXX (three strikes), the total value is 30.

Requirements:
Write code to keep track of the score of a game of bowling. There are 2 functionalities we want:
* score(Rolls: array) : takes the rolls for 1 game and returns int is called only at the very end of the game. It returns the total score for that game.
* best score(Games: 2-D array): takes a set of games, and returns the best score out of them.

Let's play a fixed 10 frame game for the purpose of this exercise. If the game is not a 10 frame game, throw a valid error.
And write good function and file names, follow the best coding and naming conventions for JS.

Sample Test cases for score functionality
Testcase1:
Rolls: 3, 6, 3, 6, 3, 6, 3, 6, 3, 6, 3, 6, 3, 6, 3, 6, 3, 6, 3, 6
Score: 90
Frames: {1: [3,6], 2: [3,6], ... and so on}

Testcase2:
Rolls: 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 10, 10, 10
Score: 30
Frames: {1:[0,0], 2:[0,0], ... , 10: [10,10,10]}


Testcase3:
Rolls: 6, 4, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0
Score: 16
Frames: {1:[6,4,3], 2:[3,0], ... , 10: [0,0]}

Sample Test cases for best score functionality

 */
public class Bowling {
    private static final int STRIKE_SCORE = 10;

    private static HashMap<Integer, List<Integer>> getFrames(List<Integer> rolls) {
        int frameCounter = 1, index = 0;
        HashMap<Integer, List<Integer>> frames = new HashMap<>();

        while (index < rolls.size() - 1) {
            List<Integer> currentFrame = new ArrayList<>();

            // case 1: strike
            if (rolls.get(index) == STRIKE_SCORE) {
                currentFrame.add(rolls.get(index));
                currentFrame.add(rolls.get(index + 1));
                currentFrame.add(rolls.get(index + 2));
                index += 1;
            }
            // case 2: spare
            else if (rolls.get(index) + rolls.get(index + 1) == STRIKE_SCORE) {
                currentFrame.add(rolls.get(index));
                currentFrame.add(rolls.get(index + 1));
                currentFrame.add(rolls.get(index + 2));
                index += 2;
            }
            // case 3: normal case
            else {
                currentFrame.add(rolls.get(index));
                currentFrame.add(rolls.get(index + 1));
                index += 2;
            }

            frames.put(frameCounter, currentFrame);

            frameCounter++;
            if (frameCounter > 10) {
                break;
            }
        }

        return frames;
    }

    private static int getScore(List<Integer> rolls) {
        HashMap<Integer, List<Integer>> frames = getFrames(rolls);
        int sum = 0;
        for (List<Integer> frameRolls : frames.values()) {
            sum += frameRolls.stream().mapToInt(i -> i).sum();
        }
        return sum;
    }

    public static void main(String[] args) {
        List<Integer> rolls1 = Arrays.asList(3, 6, 3, 6, 3, 6, 3, 6, 3, 6, 3, 6, 3, 6, 3, 6, 3, 6, 3, 6);
        System.out.println("Frames: " + getFrames(rolls1));
        System.out.println("Score: " + getScore(rolls1));

        List<Integer> rolls2 = Arrays.asList(3, 6, 3, 6, 3, 6, 3, 6, 3, 6, 3, 6, 3, 6, 3, 6, 3, 6, 10, 3, 6);
        System.out.println("Frames: " + getFrames(rolls2));
        System.out.println("Score: " + getScore(rolls2));

        List<Integer> rolls3 = Arrays.asList(10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10);
        System.out.println("Frames: " + getFrames(rolls3));
        System.out.println("Score: " + getScore(rolls3));

        List<Integer> rolls4 = Arrays.asList(10, 9, 1, 10, 4, 5, 3, 6, 4, 4, 1, 3, 10, 1, 9, 5, 5, 2);
        System.out.println("Frames: " + getFrames(rolls4));
        System.out.println("Score: " + getScore(rolls4));

        List<Integer> rolls5 = Arrays.asList(10, 5, 5, 9, 0);
        System.out.println("Frames: " + getFrames(rolls5));
        System.out.println("Score: " + getScore(rolls5));
    }
}
