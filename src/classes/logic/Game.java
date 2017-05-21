package logic;

import javafx.animation.AnimationTimer;
import javafx.geometry.Bounds;
import logic.barriers.Barrier;
import logic.bonuses.Bonus;
import logic.platforms.Platform;

import java.util.List;

public class Game {
    private static int LEVEL_HEIGHT = 10;   //Высота каждого уровня (кол-во платформ на уровень)
    private boolean gameOver;               //Игра закончена или еще идет
    private LevelGenerator levelGenerator;    //Генератор карты
    private Character character;             //Персонаж
    private final AnimationTimer timer;     //Игровое время

    private int score;                      //Очки
    private int bonusScore;                 //Бонусы
    public final int GAME_WIDTH;            //Ширина игрового поля
    public final int GAME_HEIGHT;           //Высота игрового поля
    private static Game instance;           //Статическое поле игры, игра одна, поэтому нет смысла создавать много
    private int minPosition;
    private RecordsLoaderAndSaver recordsLoaderAndSaver;


    /**
     * ПОПРОБОВАТЬ НАЙТИ ИНОЙ СПОСОБ ОБНОВЛЕНИЯ КАРТЫ В MainGame
     *
     * @return
     */
    public int getLastPlatformY() {
        return levelGenerator.getLastPlatformY();
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
        recordsLoaderAndSaver = RecordsLoaderAndSaver.getInstance();

        setUpGame();
        timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                update();
            }
        };
    }


    /**
     * НАСТРОЙКА УРОВНЯ
     * Ставим уровень на 1
     * Перезагружаем карту
     * Ставим персонажа посередине экрана
     * ближе к нижней части
     */
    private void setUpGame() {
        gameOver = false;
        character = Character.getInstance();
        character.setTranslateY(0);
        character.setTranslateX(GAME_WIDTH / 2);
        levelGenerator.levelDistributor();
    }


    /**
     * УПРАВЛЯЮЩИЕ МЕТОДЫ
     */

    public void play() {
        bonusScore = 0;
        score = 0;
        gameOver = false;
        timer.start();
        character.jump();
    }

    //Пауза
    public void pause() {
        timer.stop();
        for (Platform platform : levelGenerator.getLevel().getPlatforms()) {
            platform.pause();
        }
        for (Bonus bonus : levelGenerator.getLevel().getBonuses()) {
            bonus.pause();
        }
        for (Barrier barrier : levelGenerator.getLevel().getBarriers()) {
            barrier.pause();
        }
        character.pause();
    }

    public void continueGame() {
        for (Platform platform : levelGenerator.getLevel().getPlatforms()) {
            platform.continueIt();
        }
        for (Bonus bonus : levelGenerator.getLevel().getBonuses()) {
            bonus.continueIt();
        }
        for (Barrier barrier : levelGenerator.getLevel().getBarriers()) {
            barrier.continueIt();
        }
        character.continueIt();
        timer.start();
    }


    /**
     * МЕТОДЫ, ВЫЗЫВАЕМЫЙ В КАЖДОМ КАДРЕ ИГРЫ
     * Если игра не завершена вызываем методы обработки:
     * столкновений с платформами - collisionWithPlatforms
     * столкновений с препятствиями - collisionWithBarriers
     * столкновений с бонусами - collisionWithBonuses
     * <p>
     * Далее обновляется минимальная позиция, в которой может
     * находится игрок, там же идет подсчет очков
     * <p>
     * Если игрок приближается к последней платформе, то карта
     * обновляется
     * <p>
     * При завершении игры вызывается метод over
     */
    private void update() {
        if (!gameOver) {
            Bounds ub = character.getBoundsInParent();

            collisionWithPlatforms(ub);
            collisionWithBonuses(ub);
            collisionWithBarriers(ub);

            if (!character.isFalling()) {
                minPosition = (int) character.getTranslateY();
                int newScore = (int) -character.getTranslateY() / 100;
                if (newScore > score) score = newScore;
            }
            if (character.getTranslateY() < levelGenerator.getLastPlatformY() + 150) {
                updateMap();
            }
        } else over();
    }


    /**
     * ОБРАБОТКА СТОЛКНОВЕНИЙ С ПЛАТФОРМАМИ
     */
    private void collisionWithPlatforms(Bounds ub) {
        for (Platform platform : levelGenerator.getLevel().getPlatforms()) {
            if (character.isFalling()) {
                Bounds pb = platform.getBoundsInParent();
                //Страшные условия. Короче, прыгать, когда низ персонажа оказывается
                // внутри платформы, и он сам находится хотя бы на 3/4 внутри платформы
                if ((pb.getMinX() < (ub.getMaxX() - character.getWidth() / 4) &
                        pb.getMaxX() > (ub.getMinX()) + character.getWidth() / 4) &
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
    }

    /**
     * ОБАБОТКА СТОЛКНОВЕНИЙ С БОНУСАМИ
     */
    private void collisionWithBonuses(Bounds ub) {
        for (Bonus bonus : levelGenerator.getLevel().getBonuses()) {
            Bounds bb = bonus.getBoundsInParent();
            if (bb.intersects(ub)) {
                bonusScore += bonus.getScore();
                character.plusCountOfFire(bonus.getFiresCount());
                bonus.vanish();
            }
        }
    }

    /**
     * ОБРАБОТКА СТОЛКНОВЕНИЙ С ПРЕПЯТСТВИЯМИ
     */
    private void collisionWithBarriers(Bounds ub) {
        for (Barrier barrier : levelGenerator.getLevel().getBarriers()) {
            Bounds bb = barrier.getBoundsInParent();
            if (bb.intersects(ub)) {
                gameOver = true;
            }

            for (Character.Fire fire : character.getFires()) {
                if (!fire.isKilled() & bb.intersects(fire.getBoundsInParent())) {
                    barrier.kill();
                    fire.kill();
                    bonusScore += 10;
                }
            }
        }
    }

    public void over() {
        timer.stop();
        character.stop();
        character.setTranslateY(0);
        character.setTranslateX(GAME_WIDTH / 2);
        levelGenerator.resetLastPlatformY();
        recordsLoaderAndSaver.add("-------",getScore());
    }

    public List<RecordsLoaderAndSaver.Record> getRecords() {
        return recordsLoaderAndSaver.getRecords();
    }

    private void updateMap() {
        levelGenerator.updateLevel();
        levelGenerator.levelDistributor();
        character.clearFires();
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
        return levelGenerator.getLevelNumber();
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public int getScore() {
        return score + bonusScore;
    }
}
