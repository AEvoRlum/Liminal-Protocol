/*function SunderArmorStatus(name, armor) {
    return extend(StatusEffect, name, {
        update(unit, entry) {
            this.super$update(unit, entry);
        
            unit.armor = unit.type.armor / armor;
        }
    });
};*/
// 导入必要的 Java 类
// const Stat = Java.type("mindustry.world.meta.Stat");

function SunderArmorStatus(name, armor) {
    const effect = extend(StatusEffect, name, {
        update(unit, entry) {
            // 必须先调用父类更新，以确保 entry.time 递减
            this.super$update(unit, entry);
            const key = "originalArmor_" + name;
            if (unit.customData && unit.customData[key] !== undefined) {
                unit.armor = unit.customData[key] / armor;
            } else {
                // 如果未记录，则尝试记录（防御性编程）
                if (unit.customData) {
                    unit.customData[key] = unit.type.armor;
                    unit.armor = unit.customData[key] / armor;
                }
            }
        },

        applied(unit, time, extend) {
            // 调用父类方法，确保效果状态正常
            this.super$applied(unit, time, extend);
            if (!unit.customData) unit.customData = {};
            const key = "originalArmor_" + name;
            if (unit.customData[key] === undefined) {
                unit.customData[key] = unit.type.armor;
                unit.armor = unit.customData[key] / armor;
            }
        },

        onRemoved(unit) {
            const key = "originalArmor_" + name;
            if (unit.customData && unit.customData[key] !== undefined) {
                unit.armor = unit.customData[key];
                delete unit.customData[key];
            }
            this.super$onRemoved(unit);
        },

        setStats() {
            this.super$setStats();
            const Stat = Java.type("mindustry.world.meta.Stat");
            const StatUnit = Java.type("mindustry.world.meta.StatUnit");
            const reductionPercent = Math.floor((1 - 1/armor) * 100);
            this.stats.add(Stat.armor, reductionPercent + "%", StatUnit.none);
        }
    });
    return effect;
}
SunderArmorStatus("SunderArmor", 2);