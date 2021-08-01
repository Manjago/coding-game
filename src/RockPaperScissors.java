import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

/*
2 2 1
PC
PC
 */
public class RockPaperScissors {

    private static final int WIN = 1;
    private static final int LOST = -1;
    private final int width;
    private final int height;
    private final int moves;
    private final int[][] relations = new int[5][5];
    private final Actor[][] board;
    private final Actor[][] next;

    public RockPaperScissors(int width, int height, int moves) {
        this.width = width;
        this.height = height;
        this.moves = moves;

        board = new Actor[height][width];
        next = new Actor[height][width];

        win(Actor.SCISSORS, Actor.PAPER);
        win(Actor.PAPER, Actor.ROCK);
        win(Actor.ROCK, Actor.LIZARD);
        win(Actor.LIZARD, Actor.SPOCK);
        win(Actor.SPOCK, Actor.SCISSORS);
        win(Actor.SCISSORS, Actor.LIZARD);
        win(Actor.LIZARD, Actor.PAPER);
        win(Actor.PAPER, Actor.SPOCK);
        win(Actor.SPOCK, Actor.ROCK);
        win(Actor.ROCK, Actor.SCISSORS);

    }

    public static void main(String[] args) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            final StringTokenizer stringTokenizer = new StringTokenizer(reader.readLine());
            final int w = Integer.parseInt(stringTokenizer.nextToken());
            final int h = Integer.parseInt(stringTokenizer.nextToken());
            final int n = Integer.parseInt(stringTokenizer.nextToken());

            final RockPaperScissors solver = new RockPaperScissors(w, h, n);

            for (int i = 0; i < h; ++i) {
                final String line = reader.readLine();
                for (int j = 0; j < w; ++j) {
                    solver.setup(line.charAt(j), i, j);
                }
            }

            solver.solve();
            System.out.println(solver.answer());
        }
    }

    public void solve() {
        for (int k = 0; k < moves; k++) {

            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    process(i, j);
                }
            }

            if (k != (moves - 1)) {
                for (int i = 0; i < height; i++) {
                    if (width >= 0) {
                        System.arraycopy(next[i], 0, board[i], 0, width);
                    }
                }
            }

        }
    }

    private void process(int i, int j) {
        final List<Actor> pretenders = neighboors(i, j);
        final Set<Actor> winners = new HashSet<>();
        final Actor current = board[i][j];
        pretenders.forEach(actor -> {
            final int result = relations[current.index][actor.index];
            if (result == LOST) {
                winners.add(actor);
            }
        });

        if (winners.isEmpty()) {
            next[i][j] = board[i][j];
        } else if (winners.size() == 1) {
            next[i][j] = winners.stream().findAny().get();
        } else if (winners.size() == 2) {
            List<Actor> actorList = new ArrayList<>(winners);
            Actor first = actorList.get(0);
            Actor second = actorList.get(1);
            final int result = relations[first.index][second.index];
            if (result == LOST) {
                next[i][j] = second;
            } else {
                next[i][j] = first;
            }
        } else {
            throw new IllegalStateException(winners.toString());
        }
    }

    private List<Actor> neighboors(int i, int j) {
        final List<Actor> result = new ArrayList<>();

        if (i - 1 >= 0) {
            result.add(board[i - 1][j]);
        }
        if (i + 1 < height) {
            result.add(board[i + 1][j]);
        }
        if (j - 1 >= 0) {
            result.add(board[i][j - 1]);
        }
        if (j + 1 < width) {
            result.add(board[i][j + 1]);
        }
        return result;
    }

    public void setup(char actorChar, int row, int pos) {
        board[row][pos] = Actor.byChar(actorChar);
    }

    public String answer() {
        return answer(next);
    }

    private String answer(Actor[][] b) {
        final StringBuilder sb = new StringBuilder();
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                sb.append(b[i][j].symbol);
            }
            if (i != (height - 1)) {
                sb.append('\n');
            }
        }
        return sb.toString();
    }

    private void win(Actor actor, Actor other) {
        relations[actor.index][other.index] = WIN;
        relations[other.index][actor.index] = LOST;
    }

    private enum Actor {
        ROCK('R', 0),
        PAPER('P', 1),
        SCISSORS('C', 2),
        LIZARD('L', 3),
        SPOCK('S', 4);

        private final char symbol;
        private final int index;

        Actor(char symbol, int index) {
            this.symbol = symbol;
            this.index = index;
        }

        public static Actor byChar(char c) {
            switch (c) {
                case 'R':
                    return Actor.ROCK;
                case 'P':
                    return Actor.PAPER;
                case 'C':
                    return Actor.SCISSORS;
                case 'L':
                    return Actor.LIZARD;
                case 'S':
                    return Actor.SPOCK;
                default:
                    throw new IllegalArgumentException("" + c);
            }
        }

    }


}




