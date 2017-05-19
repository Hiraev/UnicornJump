package logic;

import logic.barriers.Barrier;
import logic.bonuses.Bonus;
import logic.platforms.*;

import java.util.ArrayList;
import java.util.List;

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

    @Override
    public void resetLastPlatformY() {
        lastPlatformY = 0;
    }
    @Override
    public int getLastPlatformY() {
        return lastPlatformY;
    }


    /**
     * @param height кол-во платформ
     * @param width  ширина окна
     */
    public DynamicLevelGenerator(int width, int height) {
        this.height = height;
        this.width = width;
        levelMap = updateLevel();
    }

    @Override
    public LevelMap getLevel() {
        return levelMap;
    }



    /**
     *
     */
    @Override
    public LevelMap updateLevel() {
        List<Platform> platforms = new ArrayList<>();
        List<Barrier> barriers = new ArrayList<>();
        List<Bonus> bonuses = new ArrayList<>();

        //Не вызывается при создании первого уровня
        if (levelMap != null){
            //Добавляем старые платформы, 3 штуки. Главное чтобы не случилось всяких исключений
            //ЕСЛИ РАЗМЕР КАРТЫ БУДЕТ МЕНЬШЕ 3, ТО ВСЕ СЛОМАЕТСЯ
            for (int i = levelMap.getPlatforms().size() - 3; i < levelMap.getPlatforms().size(); i++) {
                platforms.add(levelMap.getPlatforms().get(i));
            }
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

            //Сделать барьеры и бонусы!
        }
        level++;
        this.levelMap = new LevelMap(platforms, barriers, bonuses);
        return levelMap;
    }

    @Override
    public void levelDistributor() {
        platformDistributor();
        bonusDistributor();
        barrierDistributor();
    }

    //Устанавливаем позиции всех платформ
    private void platformDistributor() {
        int platformListSize = levelMap.getPlatforms().size();
        //Цикл устроен таким образом, чтобы не менять позиции платформ, которые были добавлены в предыдущем уровне
        for (int i = platformListSize - height; i < platformListSize; i++) {
            Platform platform = levelMap.getPlatforms().get(i);
            platform.play();
            platform.setTranslateY(lastPlatformY -= DISTANCE_BETWEEN_PLATFORMS);
            platform.setTranslateX(Math.random() * (width - 2 * width / 10) + width / 10);
        }
    }

    private void bonusDistributor() {
        for (Bonus bonus : levelMap.getBonuses()) {
            bonus.setTranslateX(Math.random() * (width - 2 * width / 10) + width / 10);
            bonus.setTranslateY(lastPlatformY + Math.random() * DISTANCE_BETWEEN_PLATFORMS * height);
        }
    }

    private void barrierDistributor() {
        for (Barrier barrier : levelMap.getBarriers()) {

            /**
             * Здесь нужно исключить установку координат по х для движущихся платформ
             */
            barrier.setTranslateX(Math.random() * (width - 2 * width / 10) + width / 10);
            barrier.setTranslateY(lastPlatformY + Math.random() * DISTANCE_BETWEEN_PLATFORMS * height);
            barrier.action();
        }
    }


}
