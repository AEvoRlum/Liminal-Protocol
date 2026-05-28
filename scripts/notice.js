//以下注释为VSHES荔枝独立完成为了让其他开发者同样可以更新开屏显示窗口插件
//̨̨̹̪̗͚̬͍̬͔͍̬̱̥͓̘͙ͅ复制,妮蔻拉更改,注释不变
Events.on(EventType.ClientLoadEvent, cons(e => {
    var dialog = new BaseDialog("[orange]Liminal Protocol\n阈限协议"); //新建一个显示窗口
    dialog.cont.image(Core.atlas.find("lp-图标")).row();;
        dialog.buttons.button("@close", run(() => {
        dialog.hide() //退出此界面
    })).size(128, 64); //按钮用原版@close贴图
    /*dialog.cont.button("加入我们\nQQlink", run(() => {
        Core.app.openURI("https://qun.qq.com/universal-share/share?ac=1&authKey=IfaVu5ChX7FUvzMoPR1XvsNSkbPrK3wzYzY6QuCWAxtbBPLav9GasFAG%2BCZ1j%2FPA&busi_data=eyJncm91cENvZGUiOiIxOTg5NTgwMjUiLCJ0b2tlbiI6IkdpemV0UFlZdEQ4UUdEVWprMXhLK2U5MEpKTVoreDAyc0s2UVdRM2tZellXcHJCdWgrd1pPaGQ3WmI3Q1grTzciLCJ1aW4iOiIyNDI1OTg2OTAyIn0%3D&data=6HlOtLgjWEsY_5cacVuc7idhRLkJZoDRJsnxr3msAgP2Rw9o-ZzHjQA4t5eW_KGdBqDhLmaMS8dk-Ub7S-Gy5A&svctype=4&tempid=h5_group_info");
    })).size(128, 70).pad(3); //添加qq群功能为荔枝VSHES添加      */
    dialog.cont.pane((() => {
        var table = new Table();
        //table.image(Core.atlas.find("lp-图标")).left().size(128, 128).pad(3).row();
        table.add("[pink]持续摸鱼中\n[red]模组部分音效来源于其他mod，部分贴图参考其他Mod，我真的不会画QwQ\n\n注意：\n\n与其他Mod混合会导致一些不可避免的Bug\n\n与其他Mod混合会导致一些不可避免的Bug\n\n与其他Mod混合会导致一些不可避免的Bug\n\n使用Helium.beta-1.5与本Mod混模易导致崩溃\n\n\n\n\n[#FFFFFF30]快去玩明日方舟 ~ \n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n").left().growX().wrap().width(550).maxWidth(550).pad(16).labelAlign(Align.left);
        table.row();
        return table;
    })()).grow().center().maxWidth(600);
    dialog.buttons.button("[green]Mod部分设定", run(() => {
        var dialog2 = new BaseDialog("[orange]Liminal Protocol\n[green]Mod部分设定");
        dialog2.cont.pane((() => {
            var table = new Table();
            table.add("[red]MOD中部分设定带有不合理性\n请勿过度解读\n\n\n\n\n[red]（没有等级排序，排序不代表强度）\n[orange]#炮塔标签：\n[gray]（炮塔：[炮塔标签，伤害标签]\/\/\/\/\/描述\/\/\/\/\/优点）\n\n[white]#穿甲炮：发射的子弹必定带有护甲穿透\n\n#蓄能炮：发射子弹前（装填满后）必定要蓄能一段时间\n\n#干扰炮：发射的子弹必定带有debuff（负面效果、状态）或能使目标电网减速\n\n#重弹炮：发射的子弹必定带有击退\n\n#抛射炮：子弹能够能定点打击\n\n#激光炮：发射的子弹必定是激光、持续激光或定点激光，必带有对护盾增伤\n\n#震撼炮：目标进入范围内立即攻击，必定带有减速类debuff，造成的伤害较低且必定带有护甲脆弱\n\n#追踪炮：发射的子弹必定带有追踪\n\n#动能炮：发射的子弹必定为“轨道炮”子弹，且必定带有护甲穿透\n\n#修复炮：能够修复己方单位或建筑\n\n#复合炮：一次发射多种子弹\n\n\n\n\n[orange]#伤害标签：\n\n[blue]#直击伤：仅伤害\n\n#穿甲伤：必定带有护甲穿透\n\n#能量伤：必定带有对护盾增伤和护甲脆弱\n\n#溅射伤：必定带有范围伤害\n\n#重击伤：必定带有较低值的对护盾增伤、对建筑增伤和护甲脆弱\n\n#动能伤：必定带有击退、单位穿透、护甲穿透和护盾减伤\n\n#特殊伤：[red]Error[blue]没有适合标签\n\n\n\n\n[orange]#建筑标签：\n[gray]（主标签>副标签>作用）\n（建筑：[建筑标签，建筑副标签]\/\/\/\/\/描述\/\/\/\/\/作用）\n\n[green]#运输 =>> 物品/流体/单位/热量 =>：运输、物流\n\n#储存 =>> 物品/流体 =>：储存\n\n#钻头 =>> 物品/流体 =>：采集、挖掘、获取\n\n#电力 =>> 运输/生产/储存 =>：被需求、被消耗\n\n#工厂 =>> 物品/流体/单位/热量 =>：被需求、被消耗\n\n#墙 =>> 防护 =>：抵挡、保护\n\n#核心 =>> null =>：[red]根源、一切\n\n\n\n\n[orange]#单位标签：\n[gray]（单位：[威胁等级]（非己方）\/\/\/\/\/[单位标签，单位副标签]\/\/\/\/\/描述\/\/\/\/\/能力（描述）\/\/\/\/\/武器 => [炮塔标签，伤害标签]\/\/\/\/\/应对方式（敌方））\n\n[yellow]单位标签：\n[gray]（均为(以下单位标签)  +  “单位”\n（主类 > 单位标签）\n\n[cyan]陆军 => #机甲 <> #“爬行”机甲 <> #坦克 <> #悬浮 <> #堡垒\n\n空军 => #飞行 <> #轰炸 <> #堡垒\n\n海军 => 暂无\n\n[yellow]单位副标签：\n\n[lightgray]#攻击 <> #修复 <> #建造 <> #采集 <> #保护 <> #增幅\n#狙击 <> #阻击 <> #支援 <> #运输 <> #压制 <> #突袭\n\n\n\n\n[orange]物品标签：\n[gray]（物品：[物品标签]\/\/\/\/\/描述）\n\n[stat]#建筑材料：主要应用于建筑\n\n#芯片材料：主要应用于建筑和单位制造\n\n#科技树材料：仅用于解锁科技\n\n#消耗材料：应用于消耗或作为合成配件\n\n#配件材料：较特殊材料，作用于建筑或单位的功能实现，例如：更为复杂的蓄能固件：用于蓄能炮塔\n\n\n\n\n[orange]流体标签：\n[gray]（流体：[流体标签]\/\/\/\/\/描述）\n\n[accent]#冷却 <> #合成 <> #伤害 <> #弹药\n\n\n\n\n[orange]buff（效果、状态）标签：\n[gray]（buff：[buff标签]\/\/\/\/\/作用、描述）\n\n[#b03060ff]#减速：必定带有减目标移动速度、减目标开火速度或移动阻力词条\n\n#反应：能够与其他buff相互反应并造成伤害\n\n#伤害：必定使目标在buff期间持续或间隔受到伤害\n\n#增幅：必定带有正面加成词条，且必定不带有负面加成词条\n\n#超载：必定带有大幅正面加成词条，但必定带有较大幅负面加成词条").left().growX().wrap().width(550).maxWidth(550).pad(16).labelAlign(Align.left);
            table.row();
            return table;
        })()).grow().center().maxWidth(600);
        dialog2.buttons.defaults().size(200, 200);
        dialog2.addCloseButton();
        dialog2.show();
        dialog2.buttons.button("[red]敌方单位威胁等级及单位具体分级", run(() => {
            var dialog3 = new BaseDialog("[orange]Liminal Protocol\n[red]敌方单位威胁等级");
            dialog3.cont.pane((() => {
                    dialog3.cont.pane((() => {
            var table = new Table();
            table.add("[white]T1 = AR =>> #涌流[gray]#[white]#奔袭[gray]#[white]晶裂\n\n[green]T2 = TH =>> #毁坏[gray]#[green]#破失[gray]#[green]#维系[gray]#[green]#重核\n\n[blue]T3 = DS\n\n[purple]T4 = AC\n\n[yellow]T5 = AL\n\n[orange]T6 = WY\n\n[red]T7 = RI =>> #蚀序[gray]#[red]#追光者").left().growX().wrap().width(550).maxWidth(550).pad(16).labelAlign(Align.left);
            table.row();
            return table;
        })()).grow().center().maxWidth(600);
                var table = new Table();
                table.row();
                return table;
            })()).grow().center().maxWidth(600);
            dialog3.buttons.defaults().size(210, 64);
            dialog3.addCloseButton();
            dialog3.show();
        })).size(210, 64);
        dialog2.buttons.button("[#fa8072ff]数据化及折现", run(() => {
            var dialog5 = new BaseDialog("[orange]Liminal Protocol\n[#fa8072ff]数据化及折现");
            dialog5.cont.pane((() => {
                var table = new Table();
                table.add("[orange]#表现及作用：\n[accent]使物质能够“溶”于真空，并将“数据”储存于真空，但代价是：湮灭被储存的物质\n[gold]储存完成后，相当于在真空索取了一份空间备份了一个“数组”，调取时需要使用其他物质重新“拼”出一个该“数组”\n\n[orange]#“数据”：就像游戏一样，每个物质、每个晶格、每个原子都有属于它的属性\n\n[#e55454ff]#“折现”：\n真空并不真的空，它布满了虚粒子，只是它一出现就会湮灭，因此，物质数据化后，能够从真空中“取”走被储存的“数据”，但代价仍然是：等量的物质\n\n\n\n\n[red]数据化及折现的作用非常大，但同样的，代价也非常大，这是一次等价交换，一次交易\n[#3f3f3f50]也许以后能给有人创造一种物质，能够违背物质守恒定律、能量守恒定律等……\n\n").left().growX().wrap().width(550).maxWidth(550).pad(16).labelAlign(Align.left);
                table.row();
                return table;
            })()).grow().center().maxWidth(600);
            dialog5.buttons.defaults().size(210, 64);
            dialog5.addCloseButton();
            dialog5.show();
        })).size(210, 64);
        dialog2.buttons.button("[#dc143cff]矩力", run(() => {
            var dialog4 = new BaseDialog("[orange]Liminal Protocol\n[#dc143cff]矩力");
            dialog4.cont.pane((() => {
                dialog4.cont.pane((() => {
            var table = new Table();
            table.add("[orange]#矩力：\n[accent]与引力相反，使物质之间产生斥力，允许物质之间一直保持间距，极端情况下，能使物质之间的间距随时修改，且物质移向间距目标点的速度能超越光速，抵达间距目标点后不会具备惯性\n产生这种力需要极其苛刻的条件：\n[green]物质的晶格排列必须完全对应正12面体的拓扑结构\n\n\n\n\n[blue]<“矩阵”已掌握大部分矩力使用及使用场景>").left().growX().wrap().width(400).maxWidth(400).pad(16).labelAlign(Align.left);
            table.row();
            return table;
        })()).grow().center().maxWidth(440);
                var table = new Table();
                table.image(Core.atlas.find("lp-矩力")).left().size(1022, 940).pad(3).row();
                table.row();
                return table;
            })()).grow().center().maxWidth(1180);
            dialog4.buttons.defaults().size(210, 64);
            dialog4.addCloseButton();
            dialog4.show();
        })).size(210, 64);
        dialog2.show();
    })).size(210, 64);
    dialog.buttons.button("[#ffe0a5ff]Mod交流群\n[green]QQlink", run(() => {
        Core.app.openURI("https://qun.qq.com/universal-share/share?ac=1&authKey=IfaVu5ChX7FUvzMoPR1XvsNSkbPrK3wzYzY6QuCWAxtbBPLav9GasFAG%2BCZ1j%2FPA&busi_data=eyJncm91cENvZGUiOiIxOTg5NTgwMjUiLCJ0b2tlbiI6IkdpemV0UFlZdEQ4UUdEVWprMXhLK2U5MEpKTVoreDAyc0s2UVdRM2tZellXcHJCdWgrd1pPaGQ3WmI3Q1grTzciLCJ1aW4iOiIyNDI1OTg2OTAyIn0%3D&data=6HlOtLgjWEsY_5cacVuc7idhRLkJZoDRJsnxr3msAgP2Rw9o-ZzHjQA4t5eW_KGdBqDhLmaMS8dk-Ub7S-Gy5A&svctype=4&tempid=h5_group_info");
    })).size(128, 70).pad(3);
    /*[gray]#[lightgray]*/
    dialog.buttons.button("[stat]更新日志\nUpdate log", run(() => {
        var dialog6 = new BaseDialog("[orange]Liminal Protocol\n[stat]更新日志\nUpdate log");
        dialog6.cont.pane((() => {
            var table = new Table();
            table.add("[lightgray]#新增    [green]#日期    [accent]#Mod游戏内容外新增或修改    [red]#原内容修改或拓展\n\n\n[lightgray]#New    [green]#Date    [accent]#Added or modified to Mod game content    [red]#Original content is modified or expanded\n\n[#fa8072ff]──────────────────────────────────────────────────────────────\n\n[green]2026.05.26-04.15\n\n\n[lightgray]区块：裂隙\n\n[red]开启难度选项\n\n[#fa8072ff]──────────────────────────────────────────────────────────────\n\n[green]2026.05.19-15:27\n\n\n[lightgray]单位：重核、裂断、度星、雷极、噬灭\n\n工厂：高速转质钢冶炼厂\n\n[accent]炮塔标签增加\n\n[red]毁坏改动\n\n红日改动\n\n寂静改动\n\n递归改动\n\n溯光改动\n\n扰流改动\n\n涌流改动\n\n冲击改动\n\n坠星改动\n\n辉光改动\n\n晶裂改动\n\n奔袭改动\n\n维系改动\n\n破失改动\n\nEMP II改动\n\n转质钢为材料的方块研究耗材减少\n\n[#fa8072ff]──────────────────────────────────────────────────────────────\n\n[green]2026.05.14-00:57\n\n\n[lightgray]单位：维系\n\n运输：转质钢流体管道、转质钢流体桥\n\n地形：遗迹纹路-1、遗迹纹路-2、遗迹纹路线-1、遗迹纹路线-2、遗迹纹路线-3、遗迹纹路线-4、遗迹纹路线-5、遗迹纹路线-6、遗迹纹路墙-1、遗迹纹路墙-2、遗迹地基-1、遗迹地基-2、遗迹地板-1、遗迹地板-2、遗迹地板-3、遗迹地板-4、遗迹地板-5、遗迹地板-6、遗迹墙-1、遗迹墙-2\n\n[red]文本改动\nn区块拒绝地形改动\n\n阻击改动\n\n寂静改动\n\n[#fa8072ff]──────────────────────────────────────────────────────────────\n\n[green]2026.05.09-21:48\n\n\n[lightgray]炮塔：破失、递归、寂静、阻击\n\n墙：转质钢墙、大型转质钢墙\n\n[red]部分文本改动及补全\n\n标签改动\n\n斥星特效及音效改动\n\n[#fa8072ff]──────────────────────────────────────────────────────────────\n\n[green]2026.05.02-21:19\n\n\n[red]数值调整\n\n[lightgray]工厂：大型离子聚合坩埚\n\n流体：高速重钢泵\n\n炮塔：刃狱\n\n[#fa8072ff]──────────────────────────────────────────────────────────────\n\n[green]2026.04.28-00:04\n\n\n[red]文本补全\n\n转构嵌合冶金厂生产数量增加\n\n炮塔部分数值调整\n\n[lightgray]炮塔：红日、陨星\n\n[#fa8072ff]──────────────────────────────────────────────────────────────\n\n[green]2026.04.23-22:27\n\n\n[red]流纹岩系列地形改动\n\n区块图标重绘\n\n蚀晶析质厂音效调大\n\n所有墙建造速度加快\n\n[lightgray]物品：嵌轻钢、转质嵌合钢\n\n钻头：冲击钻头\n\n运输：嵌轻钢传送带、嵌轻钢交叉器、嵌轻钢传送带桥、嵌轻钢装卸器\n\n工厂：转构嵌合冶金厂\n\n[#fa8072ff]──────────────────────────────────────────────────────────────\n\n[green]2026.04.20-17:56\n\n\n[lightgray]区块：变相\n\n[#fa8072ff]──────────────────────────────────────────────────────────────\n\n[green]2026.04.19-21:49\n\n\n[red]涌流伤害下调\n\n部分描述错误修改\n\n[lightgray]单位：毁坏\n\n运输：分类器、反向分类器\n\n[#fa8072ff]──────────────────────────────────────────────────────────────\n\n[green]2026.04.18-23:42\n\n\n[red]离子聚合坩埚最高效率上调至300%\n\n部分文本改动\n\n[lightgray]运输：重钢流体管道桥\n\n物品：晶质\n\n工厂：蚀晶析质厂\n\n储存：精钢电池\n\n[#fa8072ff]──────────────────────────────────────────────────────────────\n\n[green]202.04.17-18:45\n\n\n[red]穿云数值调整\n\n[#fa8072ff]──────────────────────────────────────────────────────────────\n\n[green]2026.04.17-03:01\n\n\n[lightgray]区块：\n拒绝\n\n[red]一些特效bug修复\n\n坠星命中特效补充\n\n[#fa8072ff]──────────────────────────────────────────────────────────────\n\n[green]2026.04.16-19:24\n\n\n[lightgray]单位：\n晶裂\n\n[#fa8072ff]──────────────────────────────────────────────────────────────\n\n[green]2026.04.16-03:33\n\n\n[red]核心数据库单位分类修改\n\n单位标签新增威胁等级\n\n[#fa8072ff]──────────────────────────────────────────────────────────────\n\n[green]2026.04.15-21:31\n\n\n[lightgray]功能：\n精钢仓库\n\n[red]单位：\n涌流、奔袭、蚀序血量下降\n\n[#fa8072ff]──────────────────────────────────────────────────────────────\n\n[green]2826.04.14-22:28\n\n\n[red]为一些建筑添加了DrawBlockParts\n\n重离子激发室造价与发电量提高\n\n大、小精钢电力节点连接颜色修改\n\n[#fa8072ff]──────────────────────────────────────────────────────────────\n\n[green]2026.04.14-16:52\n\n\n[red]所有炮塔数值上调\n\n部分文本描述修改\n\n[lightgray]炮塔：\n坠星\n\n[#fa8072ff]──────────────────────────────────────────────────────────────\n\n[green]2026.04.13-18:28\n\n\n[red]所有炮塔数值上调\n\n[lightgray]炮塔：\n辉光\n\n效果：\n停顿\n\n[#fa8072ff]──────────────────────────────────────────────────────────────\n\n[green]2026.04.12-22:39\n\n\n[red]核心数据库小标签分类拓展\n\n区块：\n为何描述修改\n\n[lightgray]运输：\n小型重钢热量传输器\n小型重钢热量路由器\n\n[#fa8072ff]──────────────────────────────────────────────────────────────\n\n[green]2026.04.12-14:26\n\n\n[red]炮塔：\n冲击改为消耗电量\n斥星消耗电量减少\n\n单位：\n拓荒者文本补全\n\n区块：\n不容拒绝描述修改\n为何部分整改\n\n\n[gray]英文翻译以后开源了再加吧EwE，我热爱摸鱼\n\n[#fa8072ff]──────────────────────────────────────────────────────────────\n\n[green]2026.04.11-21:47\n\n\n[lightgray]区块：为何\n\n\nSector: Why\n\n[#fa8072ff]──────────────────────────────────────────────────────────────\n\n[green]2026.04.09-23:19\n\n\n[lightgray]炮塔：斥星\n\n\nTurret: Repulstar\n\n[#fa8072ff]──────────────────────────────────────────────────────────────\n\n[green]2026.04.09-01:50\n\n\n[red]抛射炮标签描述修改\n\n[lightgray]单位：奔袭\n\n效果：索取\n\n\n[red]ArtilleryTurret tag description modify\n\n[lightgray]Unit: Rusher\n\nStatus: Claim\n\n[#fa8072ff]──────────────────────────────────────────────────────────────\n\n[green]2026.04.07-22:30\n\n\n[red]增加更多文本描述\n\n部分特效修改\n\n修复拓荒者修复激光瞄准问题\n\n[lightgray]钻头：切削钻头\n\n\n[red]Added more text descriptions\n\nModified some effects\n\nFixed the Pioneer's repair laser aiming issue\n\n[lightgray]Production: Shear Drill\n\n[#fa8072ff]──────────────────────────────────────────────────────────────\n\n[green]2026.04.05-03:47\n\n\n[red]炮塔标签补充\n\n[lightgray]单位：追光者\n\n\n[red]Turret tag append\n\n[lightgray]Unit: Lumineseeker\n\n[#fa8072ff]──────────────────────────────────────────────────────────────\n\n[green]2026.04.05Today\n\n\n[lightgray]效果：暂停\n\n\nStatus: Stop\n\n[#fa8072ff]──────────────────────────────────────────────────────────────\n\n[green]2026.04.02Today\n\n\n[red]伤害标签补充\n\n[lightgray]效果：明灭\n\n单位：蚀序\n\n\n[red]Damage tag append\n\n[lightgray]status: Flicker\n\nUnit: Corroded Order\n\n[#fa8072ff]──────────────────────────────────────────────────────────────\n\n[green]2026.04.01今日\n\nApril.1st.2026Today\n\n\n[lightgray]效果：紊乱\n\n\nStatus: Disarray\n\n[#fa8072ff]──────────────────────────────────────────────────────────────\n\n[green]2026.03.29Today\n\nMarch.29th.2026Today\n\n\n[lightgray]墙：重钢墙[gray]#[lightgray]大型重钢墙\n\n\nWall: Massisteel Wall[gray]#[lightgray]Large Massisteel Wall\n\n[#fa8072ff]──────────────────────────────────────────────────────────────\n\n[green]2026.03.28今日\n\nMarch.28th.2026Today\n\n\n[red]区块：不容拒绝描述修改\n\n炮塔标签：榴弹炮更改为抛射炮\n\n物品标签补充\n\n[lightgray]物品：离子聚合物\n\n运输：重钢热量传输器[gray]#[lightgray]重钢热量路由器\n\n工厂：离子聚合坩埚\n\n电力：重离子激发室\n\n\n[red]Sector: Irresistible description modification\n\nTurret label: Howitzer changed to projectile gun\n\nItem labeling supplement\n\n[lightgray]Item: Ionopolymer\n\nDistribution: Massisteel Heat Redirector[gray]#[lightgray]Massisteel Heat Router\n\nCrafting: Ionopolymer Crucible\n\nPower: Heavy Ion Chamber\n\n[#fa8072ff]──────────────────────────────────────────────────────────────\n\n[green]2026.03.27今日\n\nMarch.27th.2026Today\n\n\n[red]MXrd-01部分规则修改\n\n所有名字翻译单词开头未大写全部修正\n\n电力：精钢电力节点研究条件修改\n\n[lightgray]地形：重质钢矿\n\n运输：重钢流体管道[gray]#[lightgray]重钢流体交叉器[gray]#[lightgray]重钢流体路由器\n\n流体：重钢流体容仓[gray]#[lightgray]大型重钢流体容仓[gray]#[lightgray]重钢泵\n\n工厂：重钢矿渣制热厂\n\n\n[red]Some rule modifications to MXrd-01\n\nAll names translated words are not capitalized at the beginning\n\nPower: Jinsteel power node research rlequirement modification\n\n[lightgray]Environment: Massisteel ore\n\nDistribution: Massisteel Conduit[gray]#[lightgray]Massisteel Liquid Junction[gray]#[lightgray]Massisteel Liquid Router\n\nLiquid: Massisteel Liquid Storage[gray]#[lightgray]Large Massisteel Liquid Storage[gray]#[lightgray]Massisteel Pump\n\nCrafting: Massisteel Slag Heater\n\n[#fa8072ff]──────────────────────────────────────────────────────────────\n\n[green]2026.03.26今日\n\nMarch.26th.2026Today\n\n\n[accent]模组显示名字与小描述\n\n更新日志\n\n\nModule displays name and short description\n\nUpdate log\n\n[#fa8072ff]──────────────────────────────────────────────────────────────\n\n[green]2026.03.25之前\n\n[green]March.25th.2026Before\n\n\n[lightgray]炮塔：穿云[gray]#[lightgray]溯光[gray]#[lightgray]扰流[gray]#[lightgray]冲击\n\n运输：精钢物品管道[gray]#[lightgray]精钢物品管道桥[gray]#[lightgray]溢流门[gray]#[lightgray]反向溢流门[gray]#[lightgray]装卸器\n\n钻头：精钢钻头\n\n电力：精钢电力节点[gray]#[lightgray]大型精钢电力节点\n\n墙：精钢墙[gray]#[lightgray]大型精钢墙\n\n核心：拓荒者\n\n单位：拓荒者[gray]#[lightgray]涌流\n\n物品：精钢[gray]#[lightgray]蚀晶[gray]#[lightgray]重质钢\n\n效果：EMP I[gray]#[lightgray]EMP II[gray]#[lightgray]EMP III\n\n地形：蚀晶矿[gray]#[lightgray]精钢矿[gray]#[lightgray]核心区地板[gray]#[lightgray]流纹岩[gray]#[lightgray]流纹岩墙[gray]#[lightgray]灰影岩[gray]#[lightgray]灰影岩墙[gray]#[lightgray]灰影石[gray]#[lightgray]灰影碎石[gray]#[lightgray]灰影融晶[gray]#[lightgray]流纹石[gray]#[lightgray]蚀晶簇\n\n区块：不容拒绝\n\n星球：MX108[gray]#[lightgray]MXrd-01[gray]#[lightgray]MXrd-02[gray]#[lightgray]MXrd-03\n\n[white]一些原版未翻译文本与一些原版文本更改\n\n开屏显示窗口\n\nMod部分设定\n\n敌方威胁等级及单位具体分级\n\nQQlink按钮\n\n数据化及折现详细描述\n\n矩力详细描述\n\n\n[lightgray]Turret: Cloudpiercer[gray]#[lightgray]Lucenser[gray]#[lightgray]Disflux[gray]#[lightgray]Impactor\n\nDistribution: Jynsteel duct[gray]#[lightgray]Jynsteel ductbridge[gray]#[lightgray]Jynsteel overflow[gray]#[lightgray]Jynsteel underflow[gray]#[lightgray]Jynsteel unloader\n\nProduction: Jynsteel drill\n\nPower: Jynsteel power node[gray]#[lightgray]Large Jynsteel power node\n\nWall: Jynsteel wall[gray]#[lightgray]Large Jynsteel wall\n\nCore Block: Pioneers\n\nUnit: Pioneers[gray]#[lightgray]Riptide\n\nItem: Jynsteel[gray]#[lightgray]Erocrys[gray]#[lightgray]Massisteel\n\nStatus: EMP I[gray]#[lightgray]EMP II[gray]#[lightgray]EMP III\n\nEnvironment: Core floor[gray]#[lightgray]Darkstone[gray]#[lightgray]Darkstone wall[gray]#[lightgray]Darkstone rubble[gray]#[lightgray]Rhyolitic limestone[gray]#[lightgray]Rhyolitic rubble[gray]#[lightgray]Jynsteel ore[gray]#[lightgray]Erocrys ore[gray]#[lightgray]Darkstone rock[gray]#[lightgray]Darkstone crystal[gray]#[lightgray]Erocrys crystal[gray]#[lightgray]Rhyolitic Limestone wall\n\nSector: Irresistible\n\nPlanet: MX108[gray]#[lightgray]MXrd-01[gray]#[lightgray]MXrd-02[gray]#[lightgray]MXrd-03\n\n[white]Splash Screen Window\n\nMod Settings\n\nEnemy Threat Levels and Unit Classification\n\nQQ Link Button\n\nDetailed Description of Datafication and Discounting\n\nDetailed Description of Moment Force\n(The above is AI translation)").left().growX().wrap().width(1000).maxWidth(1000).pad(16).labelAlign(Align.left);
            table.row();
            return table;
        })()).grow().center().maxWidth(1050);
        dialog6.buttons.defaults().size(200, 200);
        dialog6.addCloseButton();
        dialog6.show();
        })).size(210, 64);
    dialog.show();
}))