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

}
