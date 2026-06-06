package LP.entities.bullets;

import arc.func.Cons2;
import arc.math.Angles;
import arc.math.Mathf;
import arc.math.geom.Position;
import arc.math.geom.Vec2;
import arc.struct.Seq;
import arc.util.Tmp;
import mindustry.Vars;
import mindustry.content.Fx;
import mindustry.entities.Damage;
import mindustry.entities.Lightning;
import mindustry.entities.Units;
import mindustry.entities.bullet.BulletType;
import mindustry.gen.Bullet;
import mindustry.gen.Unit;

import LP.graphics.PositionLightning;
import LP.util.*;

public class ChainBulletType extends BulletType{
    protected static final Bullet bu = Bullet.create();

    public int maxHit = 12;
    public float chainRange = 200f;
    public float length = 200f;
    public float boltWidth = 2f;

    public int boltNum = 2;
    public boolean quietShoot = false;

    protected static final Seq<Position> points = new Seq<>();
    protected static final Vec2 tmpVec = new Vec2();

    public Cons2<Position, Position> effectController = (f, t) -> {
        PositionLightning.createEffect(f, t, hitColor, boltNum, boltWidth);
    };

    @Override
    public void init(){
        super.init();

        drawSize = Math.max(drawSize, (length + chainRange) * 2f);
    }

    @Override
    protected float calculateRange(){
        if(rangeOverride > 0) return rangeOverride;
        else return chainRange + length;
    }

    public ChainBulletType(float damage){
        super(0.01f, damage);
        despawnEffect = Fx.none;
        instantDisappear = true;
    }

    @Override
    public void init(Bullet b){
        Position target = Damage.linecast(b, b.x, b.y, b.rotation(), length);
        if(target == null)target = tmpVec.trns(b.rotation(), length).add(b);

        Position confirm = target;

        Units.nearbyEnemies(b.team, Tmp.r1.setSize(chainRange).setCenter(confirm.getX(), confirm.getY()), u -> {
            if(u.checkTarget(collidesAir, collidesGround) && u.targetable(b.team))points.add(u);
        });

        if(collidesGround){
            Vars.indexer.eachBlock(null, confirm.getX(), confirm.getY(), chainRange, t -> t.team != b.team, points::add);
        }

        if(!quietShoot || !points.isEmpty()){
            LPUtils.shuffle(points, LPUtils.rand((long)Mathf.round(confirm.getX(), 16) + 8 << Mathf.round(confirm.getY(), 16)));
            points.truncate(maxHit);
            points.insert(0, b);
            points.insert(1, target);

            for(int i = 1; i < points.size; i++){
                Position from = points.get(i - 1), to = points.get(i);
                Position sureTarget = PositionLightning.findInterceptedPoint(from, to, b.team);
                float baseAngle = Angles.angle(from.getX(), from.getY(), sureTarget.getX(), sureTarget.getY());

                effectController.get(from, sureTarget);
                Bullet hitBullet = lightningType.create(b, sureTarget.getX(), sureTarget.getY(), baseAngle);
                if (hitBullet != null) {
                    hitBullet.damage(damage);
                }

                for (int j = 0; j < lightning; j++) {
                    Lightning.create(b, ChainBulletType.this.hitColor, lightningDamage < 0f ? damage : lightningDamage, sureTarget.getX(), sureTarget.getY(),
                            baseAngle + Mathf.range(lightningCone / 2f) + lightningAngle, lightningLength + Mathf.random(lightningLengthRand));
                }

                if (fragBullet != null && fragBullets > 0) {
                    for (int j = 0; j < fragBullets; j++) {
                        float len = Mathf.random(fragOffsetMin, fragOffsetMax);
                        float angle = baseAngle + Mathf.range(fragRandomSpread / 2f) + fragAngle + fragSpread * j - (fragBullets - 1) * fragSpread / 2f;
                        fragBullet.create(b, sureTarget.getX() + Angles.trnsx(angle, len), sureTarget.getY() + Angles.trnsy(angle, len),
                                angle, Mathf.random(fragVelocityMin, fragVelocityMax), Mathf.random(fragLifeMin, fragLifeMax));
                    }
                }

                hitEffect.at(sureTarget.getX(), sureTarget.getY(), ChainBulletType.this.hitColor);

                if(sureTarget instanceof Unit)((Unit)sureTarget).apply(status, statusDuration);

                if(sureTarget != to)break;
            }
        }

        points.clear();
        b.remove();
        b.vel.setZero();
    }

    @Override
    public void hit(Bullet b, float x, float y){
    }

    @Override
    public void hit(Bullet b){
    }

    @Override
    public void despawned(Bullet b){
    }

    @Override
    public void drawLight(Bullet b){
    }

    @Override
    public void handlePierce(Bullet b, float initialHealth, float x, float y){
    }
}
