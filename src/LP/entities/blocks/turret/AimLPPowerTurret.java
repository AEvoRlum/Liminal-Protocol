package LP.entities.blocks.turret;

import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.Fill;
import arc.graphics.g2d.Lines;
import arc.math.Angles;
import arc.math.Mathf;
import arc.util.Time;
import mindustry.content.Fx;
import mindustry.entities.Effect;
import mindustry.graphics.Layer;

import LP.LPSettings;
import LP.graphics.LPPal;

public class AimLPPowerTurret extends LPPowerTurret {
    /**瞄准点*/
    public boolean drawAimPoint = false;
    public Color aimPointColor = LPPal.redMid;
    public float aimPointRadius = 8f;
    public float aimPointInnerRadius = 6f;
    public float aimPointStroke = 1.5f;
    
    /**瞄准线*/
    public boolean drawAimLine = false;
    public Color aimLineColor = LPPal.orange;
    public float aimLineWidth = 2f;
    public boolean aimLineSine = false;
    public float aimLineSineAmplitude = 3f;
    public float aimLineSinePeriod = 12f;
    
    /**瞄准效果*/
    public Effect aimEffect = Fx.none;
    public float aimEffectInterval = 60f;
    /**渲染器*/
    public AimRenderer aimRenderer = new AimRenderer();
    
    public AimLPPowerTurret(String name){
        super(name);
        hasPower = true;
    }

    @Override
    public void init(){
        super.init();
        if(aimEffect == null){
            aimEffect = new Effect(20f, e -> {
                Draw.color(aimPointColor);
                Lines.stroke(e.fout() * 3f);
                Lines.circle(e.x, e.y, e.fin() * aimPointRadius * 2f);
                Draw.color(aimPointColor, e.fout());
                Fill.circle(e.x, e.y, e.fin() * aimPointInnerRadius);
                Draw.color();
            });
        }
    }

    public class AimLPPowerTurretBuild extends LPPowerTurretBuild {
        public float aimEffectTimer = 0f;
        
        @Override
        public void draw(){
            super.draw();
            if(LPSettings.aimTurretEnabled()){
                aimRenderer.render(this);
            }
        }
        
        @Override
        public void updateTile(){
            super.updateTile();
            
            if(LPSettings.aimTurretEnabled() && drawAimPoint && aimEffect != null){
                float aimX = getAimX();
                float aimY = getAimY();
                
                if(Float.isNaN(aimX) || Float.isNaN(aimY)) return;
                
                aimEffectTimer += Time.delta;
                if(aimEffectTimer >= aimEffectInterval){
                    aimEffectTimer = 0f;
                    aimEffect.at(aimX, aimY);
                }
            }
        }
        
        public float getAimX(){
            if(unit() != null){
                return unit().aimX();
            }
            if(target != null){
                return target.getX();
            }
            return x + Angles.trnsx(rotation, range);
        }
        
        public float getAimY(){
            if(unit() != null){
                return unit().aimY();
            }
            if(target != null){
                return target.getY();
            }
            return y + Angles.trnsy(rotation, range);
        }
    }

    public static class AimRenderer {
        public boolean enabled = true;
        
        public void render(AimLPPowerTurretBuild build){
            AimLPPowerTurret turret = (AimLPPowerTurret) build.block;
            
            if(!LPSettings.aimTurretEnabled() || !enabled || !turret.drawAimPoint) return;
            
            float aimX = build.getAimX();
            float aimY = build.getAimY();
            
            if(Float.isNaN(aimX) || Float.isNaN(aimY)) return;
            
            if(turret.drawAimLine){
                Draw.z(Layer.bullet - 1);
                Draw.color(turret.aimLineColor);
                
                if(turret.aimLineSine){
                    float sin = Mathf.sin(Time.time, turret.aimLineSinePeriod, 0.3f);
                    Lines.stroke(turret.aimLineWidth * (1 + sin));
                    Drawn.drawSineLine(build.x, build.y, aimX, aimY, 
                        Time.time * 0.5f, 8, turret.aimLineSineAmplitude, 0);
                } else {
                    Lines.stroke(turret.aimLineWidth);
                    Lines.line(build.x, build.y, aimX, aimY);
                }
                Draw.color();
            }
            
            Draw.z(Layer.effect);
            Draw.color(turret.aimPointColor);

            Lines.stroke(turret.aimPointStroke);
            float squareSize = turret.aimPointInnerRadius * 1.5f + Mathf.absin(turret.aimPointInnerRadius * 1.5f, turret.aimPointInnerRadius);
            Lines.square(aimX, aimY, squareSize, 45f);

            Fill.circle(aimX, aimY, turret.aimPointInnerRadius);

            Lines.stroke(turret.aimPointStroke * 0.75f);
            LP.graphics.Drawn.circlePercentFlip(aimX, aimY, turret.aimPointInnerRadius * 2.8f, Time.time, 20f);

            Draw.color(turret.aimPointColor);
            Fill.circle(aimX, aimY, turret.aimPointInnerRadius * 0.2f);
            
            Draw.color();
        }
    }

    public static class Drawn {
        public static void drawSineLine(float x1, float y1, float x2, float y2, float time, int segments, float amplitude, float phase){
            float dx = x2 - x1;
            float dy = y2 - y1;
            float length = (float) Math.sqrt(dx * dx + dy * dy);
            float angle = Angles.angle(x1, y1, x2, y2);
            
            Lines.beginLine();
            for(int i = 0; i <= segments; i++){
                float t = (float)i / segments;
                float offset = Mathf.sin(time + t * 360f + phase) * amplitude * (1 - t);
                float x = x1 + Angles.trnsx(angle, t * length, offset);
                float y = y1 + Angles.trnsy(angle, t * length, offset);
                Lines.linePoint(x, y);
            }
            Lines.endLine();
        }
    }
}
