import javafx.animation.AnimationTimer;
import javafx.geometry.Bounds;
import platforms.Platform;

public class Game {
    private static int LEVEL_HEIGHT = 20;   //Высота каждого уровня (кол-во платформ на уровень)
    private boolean gameOver;               //Игра закончена или еще идет
    private LevelMap levelMap;              //Уровень (карта)
    private int level;                      //Уровень (номер)
    private int score;                      //Очки
    public final Character character;       //Персонаж
    private final AnimationTimer timer;     //Игровое время
    public final int GAME_WIDTH;            //Ширина игрового поля
    public final int GAME_HEIGHT;           //Высота игрового поля
    private static Game instance;           //Статическое поле игры, игра одна, поэтому нет смысла создавать много
    private int currentPlatformY;           //Позиция (Y) последней платформы
    private int minPosition;

    public static Game getInstance(int width, int height) {
        if (instance == null) {
            instance = new Game(width, height);
        }
        return instance;
    }

    private Game(int width, int height) {
        GAME_WIDTH = width;
        GAME_HEIGHT = height;
        currentPlatformY = 0;
        character = Character.getInstance();
        setUpLevel();


        platformDistributor();

        timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                update();
            }
        };
    }

    //Устанавливаем позиции всех платформ
    private void platformDistributor() {
        for (Platform platform : levelMap.getPlatforms()) {

                platform.setTranslateY(currentPlatformY -= 150);
                platform.setTranslateX(Math.random() * (GAME_WIDTH - 2 * GAME_WIDTH / 10) + GAME_WIDTH / 10);
        }
        Platform.play();
    }

    /**
     * НАСТРОЙКА УРОВНЯ
     */
    private void setUpLevel() {
        level = 3;                                                      //Ставим уровень на 1
        levelMap = new LevelMap(level, LEVEL_HEIGHT, GAME_WIDTH);   //Перезагружаем карту
                         //Ставим персонажа посередине экрана
                           //ближе к нижней части
        character.setTranslateY(0);
        character.setTranslateX(GAME_WIDTH / 2);
    }

    /**
     * УПРАВЛЯЮЩИЕ МЕТОДЫ
     */

    //Начинаем игру
    public void play() {
        timer.start();
        character.jump();
    }

    //Пауза
    public void stop() {
        timer.stop();
    }

    //Перезапуск игры
    public void restart() {
        setUpLevel();
        timer.start();
    }


    /**
     * МЕТОДЫ, ВЫЗЫВАЕМЫЙ В КАЖДОМ КАДРЕ ИГРЫ
     */
    private void update() {
        if (!gameOver) {

            if (character.isFalling()) {
                for (Platform platform : levelMap.getPlatforms()) {

                    Bounds ub = character.getBoundsInParent();
                    Bounds pb = platform.getBoundsInParent();
                    //Страшные условия. Короче, прыгать, когда низ персонажа оказывается
                    // внутри платформы, и он сам находится хотя бы на 3/4 внутри платформы
                    if ((pb.getMinX() < (ub.getMaxX() - character.getWidth() / 4) & pb.getMaxX() > (ub.getMinX()) + character.getWidth() / 4) &
                            pb.getMinY() <= ub.getMaxY() &
                            pb.getMinY() >= ub.getMinY()) {
                        character.jump();
                        platform.touch();
                    }
                }

                //Условие завершения игры
                if (character.getTranslateY() > minPosition + GAME_HEIGHT * 3 / 4) {
                    gameOver = true;
                }
            } else {
                //Меняем минимальную позицию, чтобы вычислить завершена игра или нет
                minPosition = (int) character.getTranslateY();
            }
        } else { //Если игра завершена
            timer.stop();
        }
    }

    /**
     * Геттеры
     *
     * @return character, levelMap, level
     */
    //Всякие геттеры
    public Character getCharacter() {
        return character;
    }

    public LevelMap getLevelMap() {
        return levelMap;
    }

    public int getLevel() {
        return level;
    }
    public boolean isGameOver() {
        return gameOver;
    }
}
