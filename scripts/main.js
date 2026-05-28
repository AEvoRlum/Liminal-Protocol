importPackage(Packages.mindustry.graphics)
//Vars.maxSchematicSize = 128;
Vars.maxSchematicSize = 600
if (!Vars.headless) {
	Vars.renderer.minZoom = 0.2;
	Vars.renderer.maxZoom = 64;
}
MapResizeDialog.minSize = 1
MapResizeDialog.maxSize = 600
Team.blue.name = "矩阵";
Team.crux.name = "愿景";
Team.crux.hasPalette = true;
//Team.crux.ignoreUnitCap = true;
require("planet");
//require("item");
require("notice");
//require("multicCrafterlib");
//require("status");