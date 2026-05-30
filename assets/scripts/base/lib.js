//This is a piece of shit
exports.modName = "lp"

exports.mod = Vars.mods.locateMod(exports.modName);

exports.addToResearch = (content, research) => {
    if (!content) {
        throw new Error('content is null!');
    }
    if (!research.parent) {
        throw new Error('research.parent is empty!');
    }
    var researchName = research.parent;
    var customRequirements = research.requirements;
    var objectives = research.objectives;

    var lastNode = TechTree.all.find(boolf(t => t.content == content));
    if (lastNode != null) {
        lastNode.remove();
    }

    var node = new TechTree.TechNode(null, content, customRequirements !== undefined ? customRequirements : content.researchRequirements());
    var currentMod = exports.mod;
    if (objectives) {
        node.objectives.addAll(objectives);
    }

    if (node.parent != null) {
        node.parent.children.remove(node);
    }

    // find parent node.
    var parent = TechTree.all.find(boolf(t => t.content.name.equals(researchName) || t.content.name.equals(currentMod.name + "-" + researchName)));

    if (parent == null) {
        throw new Error("Content '" + researchName + "' isn't in the tech tree, but '" + content.name + "' requires it to be researched.");
    }

    // add this node to the parent
    if (!parent.children.contains(node)) {
        parent.children.add(node);
    }
    // reparent the node
    node.parent = parent;
};

exports.bundle = (text, num, num1) => {
	if (num1) {
		return Core.bundle.format(text, num, num1);
	} else if (num) {
		return Core.bundle.format(text, num);
	} else {
		return Core.bundle.get(text);
	}
}

let fi = Vars.tmpDirectory.child("bundle_zh_CN.properties");
let mod = Vars.mods.getMod("calamity");

let contentMap = new OrderedMap(); // ctype => Seq

Vars.content.each(c => {
    if(c.minfo.mod == mod){
        let seq = contentMap.get(c.getContentType(), prov(() => new Seq()))
        seq.add(c);
    }
});

let out = new OrderedMap();

contentMap.each((ctype, seq) => {
    seq.sort(floatf(c => c.id));
    
    seq.each(c => {
        put(c, "name");
        put(c, "details");
        put(c, "description");
    })
    
    function put(content, key){
        if(content[key]) out.put(getKey(content, key), content[key]);
    }
});

function getKey(content, key){
    return content.getContentType().name() + "." + content.name + "." + key;
}

let writer = fi.writer(false);

Reflect.invoke(PropertiesUtils, "storeImpl", [out, writer, null, true, true], ObjectMap, java.io.Writer, java.lang.String, java.lang.Boolean.TYPE, java.lang.Boolean.TYPE);

writer.close();