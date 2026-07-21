package ContingencyContract.math;

import arc.math.Interp;

public class CCInterp {
    public static final Interp backOut = new Interp() {
        private static final float s = 1.70158f;
        
        @Override
        public float apply(float a) {
            a -= 1f;
            return a * a * ((s + 1f) * a + s) + 1f;
        }
    };
    
    public static final Interp backIn = new Interp() {
        private static final float s = 1.70158f;
        
        @Override
        public float apply(float a) {
            return a * a * ((s + 1f) * a - s);
        }
    };
    
    public static final Interp backInOut = new Interp() {
        private static final float s = 1.70158f * 1.525f;
        
        @Override
        public float apply(float a) {
            if (a < 0.5f) {
                a *= 2f;
                return 0.5f * a * a * ((s + 1f) * a - s);
            }
            a = a * 2f - 2f;
            return 0.5f * (a * a * ((s + 1f) * a + s) + 2f);
        }
    };
    
    public static final Interp elasticOut = new Interp() {
        private static final float p = 0.3f;
        
        @Override
        public float apply(float a) {
            if (a == 0f || a == 1f) return a;
            float s = p / 4f;
            return (float) Math.pow(2f, -10f * a) * (float) Math.sin((a - s) * (2f * Math.PI) / p) + 1f;
        }
    };
    
    public static final Interp elasticIn = new Interp() {
        private static final float p = 0.3f;
        
        @Override
        public float apply(float a) {
            if (a == 0f || a == 1f) return a;
            float s = p / 4f;
            return -(float) Math.pow(2f, 10f * (a - 1f)) * (float) Math.sin((a - 1f - s) * (2f * Math.PI) / p);
        }
    };
    
    public static final Interp overshootOut = new Interp() {
        private static final float s = 0.2f;
        
        @Override
        public float apply(float a) {
            a -= 1f;
            return a * a * ((1f + s) * a + s) + 1f;
        }
    };
    
    public static final Interp smoothOut = new Interp() {
        @Override
        public float apply(float a) {
            return 1f - (1f - a) * (1f - a) * (1f - a) * (1f - a);
        }
    };
    
    public static final Interp smoothIn = new Interp() {
        @Override
        public float apply(float a) {
            return a * a * a * a;
        }
    };
    
    public static final Interp smoothInOut = new Interp() {
        @Override
        public float apply(float a) {
            if (a < 0.5f) {
                float b = a * 2f;
                return b * b * b * b * 0.5f;
            }
            float b = (a - 0.5f) * 2f;
            return (1f - (1f - b) * (1f - b) * (1f - b) * (1f - b)) * 0.5f + 0.5f;
        }
    };
}