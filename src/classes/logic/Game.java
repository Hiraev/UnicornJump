package logic;

import javafx.animation.AnimationTimer;
import javafx.geometry.Bounds;
import logic.platforms.Platform;

public class Game {
    private static int LEVEL_HEIGHT = 20;   //Высота каждого уровня (кол-во платформ на уровень)
    private boolean gameOver;               //Игра закончена или еще идет
    private LevelGenerator levelGenerator;    //Генератор карты

    private int level;                      //Уровень (номер)
    private int score;                      //Очки
    private int bonuses;                    //Бонусы
    public Character character;             //Персонаж
    private final AnimationTimer timer;     //Игровое время
    public final int GAME_WIDTH;            //Ширина игрового поля
    public final int GAME_HEIGHT;           //Высота игрового поля
    private static Game instance;           //Статическое поле игры, игра одна, поэтому нет смысла создавать много

    private int lastPlatformY;           //Позиция (Y) последней платформы
    private int minPosition;


    /**
     * ПОПРОБОВАТЬ НАЙТИ ИНОЙ СПОСОБ ОБНОВЛЕНИЯ КАРТЫ В MainGame
     * @return
     */
    public int getLastPlatformY() {
        return lastPlatformY;
    }

    public static Game getInstance(int width, int height) {
        if (instance == null) {
            instance = new Game(width, height);
        }
        return instance;
    }

    private Game(int width, int height) {
        GAME_WIDTH = width;
        GAME_HEIGHT = height;
        levelGenerator = new DynamicLevelGenerator(width, LEVEL_HEIGHT);

        setUpGame();

        timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                update();
            }
        };
    }

    //Устанавливаем позиции всех платформ
    private void platformDistributor() {
        int platformListSize = levelGenerator.getLevel().getPlatforms().size();
        //Цикл устроен таким образом, чтобы не менять позиции платформ, которые были добавлены в предыдущем уровне
        for (int i = platformListSize - LEVEL_HEIGHT; i < platformListSize; i++) {
            Platform platform = levelGenerator.getLevel().getPlatforms().get(i);
            platform.play();
            platform.setTranslateY(lastPlatformY -= 150);
            platform.setTranslateX(Math.random() * (GAME_WIDTH - 2 * GAME_WIDTH / 10) + GAME_WIDTH / 10);
        }
    }

    /**
     * НАСТРОЙКА УРОВНЯ
     */
    private void setUpGame() {
        lastPlatformY = 0;
        gameOver = false;
        level = 1;
        //Ставим уровень на 1
        //Перезагружаем карту
        //Ставим персонажа посередине экрана
        //ближе к нижней части

        character = Character.getInstance();
        character.setTranslateY(0);
        character.setTranslateX(GAME_WIDTH / 2);
        platformDistributor();
    }


    /**
     * УПРАВЛЯЮЩИЕ МЕТОДЫ
     */

    //Начинаем игру
    public void play() {
        gameOver = false;
        timer.start();
        character.jump();
    }

    //Пауза
    public void pause() {
        timer.stop();
        for (Platform platform : levelGenerator.getLevel().getPlatforms()){
            platform.pause();
        }
        character.pause();

    }

    public void continueGame() {
        for (Platform platform : levelGenerator.getLevel().getPlatforms()){
            platform.continueAnimation();
        }
        character.continueAnimation();
        timer.start();
    }



    /**
     * МЕТОДЫ, ВЫЗЫВАЕМЫЙ В КАЖДОМ КАДРЕ ИГРЫ
     */
    private void update() {
        if (!gameOver) {


            for (Platform platform : levelGenerator.getLevel().getPlatforms()) {


                if (character.isFalling()) {

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

                    //Условие завершения игры
                    if (character.getTranslateY() > minPosition + GAME_HEIGHT * 3 / 4) {
                        gameOver = true;
                    }
                }

            }

            if (!character.isFalling()) {
                //Меняем минимальную позицию, чтобы вычислить завершена игра или нет
                minPosition = (int) character.getTranslateY();
                //Очки
                int newScore = (int) -character.getTranslateY() / 100;
                if (newScore > score) score = newScore;
            }
            //Обновляем карту при приближении к последней платформе
            if (character.getTranslateY() < lastPlatformY) {
                updateMap();
            }
        } else { //Если игра завершена
            over();
        }
    }

    public void over(){
        timer.stop();
        character.setTranslateY(0);
        character.setTranslateX(GAME_WIDTH / 2);
        level = 1;
        lastPlatformY = 0;
    }

    private void updateMap() {
        System.out.println("Update");
        System.out.println(levelGenerator.getLevel().getPlatforms().size());
        levelGenerator.updateLevel();

        platformDistributor();
        System.out.println(levelGenerator.getLevel().getPlatforms().size());
    }

    /**
     * Геттеры
     *
     * @return character, levelGenerate, level
     */
    //Всякие геттеры
    public Character getCharacter() {
        return character;
    }

    public LevelMap getLevelMap() {
        return levelGenerator.getLevel();
    }

    public int getLevel() {
        return level;
    }

    public boolean isGameOver() {
        return gameOver;
    }
}
