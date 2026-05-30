function newItem(name, color, obj) {
	Object.assign(exports[name] = new Item(name,
		Color.valueOf(color)), obj);
}
newItem("jynsteel", "5F6372", {
    /*硬度*/
    hardness: 2,
    /*放射性*/
    radioactivity: 0,
    /*爆炸性*/
    explosiveness: 0,
    /*燃烧性*/
    flammability: 0,
    /*放电性*/
    charge: 0,
    /*建筑时间消耗倍率*/
    cost: 0.4,
    /*建筑血量系数*/
    healthScaling: 1.12,
    /*动态贴图帧数*/
    frames: 0,
    /*动态贴图每张存在帧数*/
    //frameTime: 0,
    /*每两帧之间的过渡帧*/
    //transitionFrames: 0,
    /*是否作为建筑材料*/
    buildable: true,
    alwaysUnlocked: false,
    /*localizedName: "[#5F6372]Jynsteel"*/
});
newItem("erocrys", "FFB570", {
    hardness: 0.5,
    radioactivity: 0,
    explosiveness: 0,
    flammability: 0,
    charge: 0.2,
    cost: 0.5,
    healthScaling: 0.98,
    frames: 0,
    buildable: true,
    alwaysUnlocked: false,
    /*localizedName: "[#FFB570]Erocrys"*/
});
newItem("massisteel", "545F66", {
    hardness: 3,
    radioactivity: 0,
    explosiveness: 0,
    flammability: 0,
    charge: 0,
    cost: 0.8,
    healthScaling: 1.2,
    frames: 0,
    buildable: true,
    alwaysUnlocked: false,
    /*localizedName: "[#545F66]Massisteel"*/
});
newItem("ionopolymer", "E8D174", {
    hardness: 2,
    radioactivity: 0.1,
    explosiveness: 0.3,
    flammability: 0,
    charge: 1,
    cost: 1,
    healthScaling: 0
    frames: 0,
    buildable: false,
    alwaysUnlocked: false,
    /*localizedName: "[#E8D174FF]Ionopolymer"*/
});
newItem("crystalite", "EDA76E", {
    hardness: 0.5,
    radioactivity: 0.2,
    explosiveness: 0,
    flammability: 0,
    charge: 1,
    cost: 0.7,
    healthScaling: 0.1
    frames: 0,
    buildable: true,
    alwaysUnlocked: false,
});