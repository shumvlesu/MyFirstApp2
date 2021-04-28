package com.shumikhin.myfirstapp2.data;

import com.shumikhin.myfirstapp2.R;

import java.util.Random;

//Работать с изображениями мы научимся в следующем курсе, а пока просто сделаем преобразование
//идентификатора изображения в порядковый номер и обратно. Это сделано, чтобы точно установить,
//какая картинка будет на карточке. Дело в том, что студия не гарантирует, что идентификаторы не
//будут меняться от версии к версии приложения.

public class PictureIndexConverter {
    private static Random rnd = new Random();
    private static Object syncObj = new Object();
    private static int[] picIndex = {R.drawable.cat1,
            R.drawable.cat2,
            R.drawable.cat3,
            R.drawable.cat4,
            R.drawable.cat5,
            R.drawable.cat6,
            R.drawable.cat7,
    };

    public static int randomPictureIndex() {
        synchronized (syncObj) {
            return rnd.nextInt(picIndex.length);
        }
    }

    public static int getPictureByIndex(int index) {
        if (index < 0 || index >= picIndex.length) {
            index = 0;
        }
        return picIndex[index];
    }

    public static int getIndexByPicture(int picture) {
        for (int i = 0; i < picIndex.length; i++) {
            if (picIndex[i] == picture) {
                return i;
            }
        }
        return 0;
    }
}

