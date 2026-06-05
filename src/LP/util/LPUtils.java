package LP.util;

import arc.math.Mathf;
import arc.math.Rand;
import arc.struct.Seq;
public final class LPUtils{
    public static final Rand rand = new Rand(0);

    private LPUtils(){
    }

    public static Rand rand(long id){
        rand.setSeed(id);
        return rand;
    }

    public static <T> void shuffle(Seq<T> seq, Rand rand){
        T[] items = seq.items;
        for(int i = seq.size - 1; i >= 0; i--){
            int ii = Mathf.random(i);
            T temp = items[i];
            items[i] = items[ii];
            items[ii] = temp;
        }
    }
}