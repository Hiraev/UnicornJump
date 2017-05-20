package logic;

import logic.barriers.Barrier;
import logic.barriers.MoveBarrier;
import logic.barriers.StaticBarrier;
import logic.bonuses.Bonus;
import logic.bonuses.RainbowBonus;
import logic.bonuses.StarBonus;
import logic.platforms.*;

import java.util.*;

/**
 * ДИНАМИЧЕСКИЙ ГЕНЕРАТОР КАРТЫ
 * СОЗДАЕТ УРОВНИ ПО МЕРЕ ПРОДВИЖЕНИЯ ИГРОКА ВВЕРХ
 */

public class DynamicLevelGenerator implements LevelGenerator {
    private int height;
    private int width;
    private LevelMap levelMap;
    private int level = 1;
    private int lastPlatformY;
    private int DISTANCE_BETWEEN_PLATFORMS = 150;
    private Deque<Platform> platformsDeque;
    private Deque<Bonus> bonusesDeque;
    private Deque<Barrier> barriersDeque;
    private List<Platform> platforms;
    private List<Barrier> barriers;
    private List<Bonus> bonuses;


    private int platformDequeSize;
    private int barriersDequeSize;
    private int bonusesDequeSize;

    private List<Integer> positionsForBarriers;
    private List<Integer> positionsForBonuses;


    /**
     * @param height кол-во платформ
     * @param width  ширина окна
     */
    public DynamicLevelGenerator(int width, int height) {
        this.height = height;
        this.width = width;

        platformsDeque = new ArrayDeque<>();
        barriersDeque = new ArrayDeque<>();
        bonusesDeque = new ArrayDeque<>();

        platforms = new ArrayList<>();
        barriers = new ArrayList<>();
        bonuses = new ArrayList<>();

        //СОДЕРЖАНИЕ ЭТИХ СПИСКОВ ОПРЕДЕЛЯЕТСЯ КОЛ-ВОМ ПЛАТФОРМ НА УРОВЕНЬ
        positionsForBarriers = Arrays.asList(0,3,2,5,4,7,1,8,9,6);
        positionsForBonuses = Arrays.asList(4,3,6,5,8,9,1,2,7,0);

        levelMap = new LevelMap(platforms, barriers, bonuses);
        updateLevel();
    }

    /**
     * МОТОД, ВЫЗЫВАЕМЫЙ ИЗ ИГРЫ, КОТОРЫЙ СИГНАЛИЗИУЕТ
     * О ЗАВЕРШЕНИИ ИГРЫ. ЗДЕСЬ ОБНУЛЯЕТСЯ ПОЗИЦИЯ ПОСЛЕДНЕЙ
     * ПЛАТФОРМЫ И ОЧИЩАЮТСЯ ОЧЕРЕДИ.
     */
    @Override
    public void resetLastPlatformY() {
        platformsDeque.clear();
        barriersDeque.clear();
        bonusesDeque.clear();
        lastPlatformY = 0;
        level = 1;
    }

    @Override
    public int getLastPlatformY() {
        return lastPlatformY;
    }

    @Override
    public int getLevelNumber() {
        return level - 1;
    }

    @Override
    public LevelMap getLevel() {
        return levelMap;
    }


    /**
     * ПЕРЕД КАЖДЫМ РАСПРЕДЕЛЕНИЕ ПЕРЕМЕШИВАЕМ СПИСКИ
     * ЧТОБЫ БАРЬЕРЫ И БОНУСЫ РАСПОЛАГАЛИСЬ СЛУЧАЙНМ ОБРАЗОМ
     */
    @Override
    public void levelDistributor() {
        long seed = System.nanoTime();
        Collections.shuffle(positionsForBonuses, new Random(seed));
        Collections.shuffle(positionsForBarriers, new Random(seed));
        platformDistributor();
        bonusDistributor();
        barrierDistributor();
    }

    /**
     *
     */
    @Override
    public void updateLevel() {
        platforms.clear();
        barriers.clear();
        bonuses.clear();

        platformDequeSize = platformsDeque.size();
        barriersDequeSize = barriersDeque.size();
        bonusesDequeSize = bonusesDeque.size();

        while (!platformsDeque.isEmpty()) {
            platforms.add(platformsDeque.poll());
        }
        while (!barriersDeque.isEmpty()) {
            barriers.add(barriersDeque.poll());
        }
        while (!bonusesDeque.isEmpty()) {
            bonuses.add(bonusesDeque.poll());
        }


        //ПЕРЕПИСАТЬ ВСЮ ЭТУ ДИЧЬ

        for (int i = 0; i < height; i++) {
            double random = Math.random();
            double random2 = Math.random();
            double probability = Math.abs(random * (double) level);
            if (probability > 0.3) {
                if (random2 > 0.2) platforms.add(new BasicPlatform());
                else platforms.add(new MovePlatform(width));
            } else if (probability > 0.1) {
                if (random2 > 0.2) platforms.add(new MovePlatform(width));
                else platforms.add(new VanishPlatform(width));
            } else if (probability > 0.05) {
                if (random2 > 0.2) platforms.add(new VanishPlatform(width));
                else platforms.add(new BasicFadePlatform());
            } else if (probability > 0.01) {
                if (random2 > 0.2) platforms.add(new BasicFadePlatform());
                else platforms.add(new MoveFadePlatform(width));
            } else {
                if (random2 > 0.4) platforms.add(new MoveFadePlatform(width));
                else if (random2 > 0.2) platforms.add(new BasicFadePlatform());
                else if (random2 > 0.08) platforms.add(new MovePlatform(width));
                else platforms.add(new BasicPlatform());
            }
        }


        /**
         * СОЗДАЕМ БАРЬЕРЫ И БОНУСЫ
         */
        for (int i = 0; i < height - 1; i++) {
            double randomForBarrier = Math.random() * (1 - ((double) (1 / level)));
            if (randomForBarrier > 0.9) barriers.add(new MoveBarrier(width));
            else if (randomForBarrier > 0.7) barriers.add(new StaticBarrier(width));

            double randomForBonus = Math.random();
            if (randomForBonus > 0.95) bonuses.add(new RainbowBonus(width));
            else if (randomForBonus > 0.9) bonuses.add(new StarBonus(width));
        }


        for (int i = platformDequeSize; i < platforms.size(); i++) {
            platformsDeque.add(platforms.get(i));
        }
        for (int i = barriersDequeSize; i < barriers.size(); i++) {
            barriersDeque.add(barriers.get(i));
        }

        for (int i = bonusesDequeSize; i < bonuses.size(); i++) {
            bonusesDeque.add(bonuses.get(i));
        }
        level++;
    }

    /**
     * ВО ВСЕХ ДИСТРИБЬЮТОРАХ ПРОПУСКАЮТСЯ ПЛАТФОРМЫ, БАРЬЕРЫ И БОНУСЫ, КОТОРЫЕ
     * БЫЛИ РАССТАВЛЕНЫ ПРИ ПРЕДЫДУЩЕМ ЗАПУСКЕ ДИСТРИБЬЮТОРОВ. ЭТО ПРОИСХОДИТ
     * ЗА СЧЕТ ТОГО, ЧТО ИСПОЛЬЗУЮТСЯ ПЕРЕМЕННЫЕ РАЗМЕРЕ ОЧЕРЕДЕЙ, В КОТОРЫХ
     * ХРАНЯТСЯ ЭЛЕМЕНТЫ ИЗ ПРЕДЫДУЩЕГО УРОВНЯ. СЧЕТЧИК ИДЕТ ОТ РАЗМЕРА
     * ОЧЕРЕДИ И ЗА СЧЕТ ЭТОГО ПРОПУСКАЮТСЯ ЭЛЕМЕНТЫ ИЗ ПРЕДЫДУЩЕГО УРОВНЯ.
     * <p>
     * ИМЕННО В ДИСТРИБЬЮТОРЕ ПЛАТФОРМ ПРОИСХОДИТ ДЕКРЕМЕНТАЦИЯ ПЕРЕМЕННОЙ
     * lastPlatformY
     */
    private void platformDistributor() {
        for (int i = platformDequeSize; i < platforms.size(); i++) {
            Platform platform = levelMap.getPlatforms().get(i);
            platform.play();
            Platform.Type platformType = platform.getType();
            if (platformType != Platform.Type.Move & platformType != Platform.Type.MoveFade) {
                platform.setTranslateX(Math.random() * (width - 2 * width / 10) + width / 10);
            }
            platform.setTranslateY(lastPlatformY -= DISTANCE_BETWEEN_PLATFORMS);
        }
    }


    /**
     * В СЛЕДУЮЩЕМ МЕТОДЕ ПРОИСХОДИТ ПОИСК МЕСТА ДЛЯ УСТАНОВКИ
     * БАРЬЕРА ИЛИ БОНУСА.
     * ПЕРВОЕ СЛАГАЕМОЕ - ЭТО МЕСТО МЕЖДУ ЛЮБЫМИ ДВУМЯ ПЛАТФОРМАМИ,
     * КОТОРЫЕ РАСПОЛОЖЕНЫ МЕЖДУ ПЕРВОЙ И ПОСЛЕДНЕЙ ПЛАФТОРАМИ.
     * <p>
     * <p>
     * ВТОРОЕ СЛАГАЕМОЕ - ЭТО МЕСТО МЕЖДУ ДВУХ ПЛАТФОРМ.
     * ОНО НАХОДИТСЯ В ДИАПАЗОНЕ 1/4 ОТ ВЕРХНЕЙ ПЛАТФОРМЫ И 1/4 ОТ НИЖНЕЙ.
     * ПРИ ВСЕХ ПОДСЧЕТАХ УЧИТЫВАЕМ РАЗМЕРЫ ПРЕГРАД, РАЗМЕРЫ ПЛАТФОРМ
     * ИГНОРИРУЕМ, ТАК КАК ПЛАТФОРЫ НЕ ТАКИЕ ШИРОКИЕ. ВЫСОТА УЧИТЫВАЕТСЯ В
     * САМИХ ДИСТРИБЬЮТОРАХ
     *
     * @param pos определяет между какими платформами будет располагаться элемент
     */
    private double bonusAndBarrierYDistribute(int pos) {
        int positionBetweenPlatforms = (pos * DISTANCE_BETWEEN_PLATFORMS);
        return (lastPlatformY + positionBetweenPlatforms) + (int) (Math.random() *
                (DISTANCE_BETWEEN_PLATFORMS - 2 * DISTANCE_BETWEEN_PLATFORMS / 4)) +
                DISTANCE_BETWEEN_PLATFORMS / 4;
    }

    private void bonusDistributor() {
        for (int i = bonusesDequeSize; i < bonuses.size(); i++) {
            Bonus bonus = bonuses.get(i);
            System.out.println(positionsForBonuses);
            bonus.setTranslateX(Math.random() * (width - 2 * width / 10) + width / 10);
            bonus.setTranslateY(bonusAndBarrierYDistribute(positionsForBonuses.get(i - bonusesDequeSize)) - bonus.getHeight() / 2);
        }
    }

    private void barrierDistributor() {
        for (int i = barriersDequeSize; i < barriers.size(); i++) {
            Barrier barrier = barriers.get(i);
            if (barrier.getType() != Barrier.Type.Move) barrier.setTranslateX(Math.random() * (width - 2 * width / 10) + width / 10);
            barrier.setTranslateY(bonusAndBarrierYDistribute(positionsForBarriers.get(i - barriersDequeSize)) - barrier.getHeight() / 2);
            barrier.action();
        }
    }
}
