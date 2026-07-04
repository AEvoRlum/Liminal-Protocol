# Liminal Protocol

Liminal Protocol is a large-scale Java Mod for Mindustry[Mindustry](https://github.com/Anuken/Mindustry), offering rich new content and gameplay experiences.

## Introduction

Liminal Protocol (LP for short) introduces brand new game mechanics and content, including:

- **New Blocks**: Various turrets, power plants, production facilities, transport pipes, etc.
- **New Units**: Player units and enemy units
- **New Weapon Systems**: Lightning chains, gravity bullets, energy weapons, etc.
- **New Effects & Sounds**: Stunning visual effects and unique sound effects
- **New Planets & Levels**: Brand new game maps, tech tree, story, etc.

## System Requirements

| Platform | Supported Version | Description |
|----------|-------------------|-------------|
| PC (Windows/Linux/macOS) | Mindustry v158.1+ | Run via Mod import |
| Android | Mindustry v158.1+ | Run via Mod import |

## Building

### Prerequisites

- JDK 17 or higher
- Android SDK (only for building Android-compatible version)

### Desktop Testing Build

```bash
gradlew jar
```

After building, the mod jar file is located in the `build/libs` directory. This version is only for desktop testing and will not work on Android.

### GitHub Actions Build (Recommended)

The project is configured with GitHub Actions CI to automatically build on every commit.

1. Push the project to a GitHub repository
2. Go to the **Actions** tab of the repository
3. Select the most recent commit
4. Download the build artifact from the **Artifacts** section (as a zip file)
5. Unzip and import into Mindustry. This version works on both Android and desktop.

### Local Full Platform Build

1. Download Android SDK and set the `ANDROID_HOME` environment variable
2. Install API level 30 and any version of build tools (e.g., 30.0.1)
3. Add the build-tools directory to PATH
4. Run the build command:

```bash
gradlew deploy
```

After building, the jar file in `build/libs` can run on both Android and desktop.

## Project Structure

```
Liminal-Protocol/
├── assets/                    # Assets
│   ├── bundles/               # Localization text
│   ├── maps/                  # Game maps
│   ├── schematics/            # Schematics
│   ├── scripts/               # Script files (disabled)
│   ├── sounds/                # Sound effects
│   └── sprites/               # Sprite assets
│       ├── blocks/            # Block textures
│       ├── effects/           # Effect textures
│       ├── items/             # Item icons
│       ├── units/             # Unit textures
│       └── ui/                # UI elements
├── content/                   # HJSON configuration files (disabled)
├── src/
│   └── LP/                    # Java source code
│       ├── content/           # Content registration
│       │   ├── LPBlocks.java  # Block registration
│       │   ├── LPItems.java   # Item registration
│       │   ├── LPUnits.java   # Unit registration
│       │   └── LPFx.java      # Effect registration
│       ├── entities/          # Entity definitions
│       │   ├── blocks/        # Block logic
│       │   ├── bullets/       # Bullet types
│       │   └── units/         # Unit logic
│       ├── graphics/          # Graphics rendering
│       │   ├── Drawn.java     # Custom drawing utilities
│       │   └── PositionLightning.java # Position-based lightning system
│       ├── planet/            # Planet generator
│       ├── ui/                # UI components
│       ├── util/              # Utility classes
│       └── LPMod.java         # Mod entry point
└── build.gradle               # Gradle build configuration
```

## Key Features

### Block System
- **AnnihilationReactor**: Advanced power plant with risk-reward mechanics
- **MultiCrafter**: Multi-recipe factory with flexible production system
- **SquareRangePowerNode**: Square-range power node

### Bullet System
- **LightningLinkBulletType**: Lightning chain bullets that continuously damage multiple targets
- **GravitationLightningLinkBulletType**: Gravity bullets that attract or repel units
- **EnergyBulletType**: Energy bullets with armor-piercing damage
- **And more bullet types**

### Graphics System
- **PositionLightning**: Position-based lightning generation system
- **Drawn**: Custom triangle and effect drawing utilities
- **TrailEffect**: Trail effect system

## Adding Dependencies

All dependencies on Mindustry, Arc, or its submodules must be declared as `compileOnly` in Gradle:

```groovy
compileOnly "com.github.Anuken:Mindustry:v158.1"
```

Only use `implementation` if you want to package another Java library *with your mod* and that library is not present in Mindustry already.

---

# QQ Groups
+ **[Proxima Centauri](https://github.com/128OTEML/ProximaCentauri) / Liminal Protocol Mod Discussion: 1085707299**

+ **A chaotic group, not recommended: 198958025**

# Contributors
+ **[128OTEML](https://github.com/128OTEML): Technical Advisor**
+ **[Martian238](https://github.com/Martian238): Original Json Content Inspiration**
+ **[laomoze](https://github.com/laomoze/WarHammer): Texture and Effect Inspiration**

---

# Liminal Protocol

Liminal Protocol 是一个为 Mindustry 打造的大型 Java Mod，提供丰富的新内容和玩法体验。

## 简介

Liminal Protocol（简称 LP）引入了全新的游戏机制和内容，包括：

- **新方块**：多种炮塔、发电厂、生产设施、传输管道等
- **新单位**：玩家单位和敌方单位
- **新武器系统**：闪电链、引力弹、能量武器等
- **新特效与音效**：精美的视觉效果和独特的音效
- **新行星与关卡**：全新的游戏地图、科技树、剧情等

## 运行环境

| 平台 | 支持版本 | 说明 |
|------|---------|------|
| PC (Windows/Linux/macOS) | Mindustry v158.1+ | 通过 Mod 导入运行 |
| Android | Mindustry v158.1+ | 通过 Mod 导入运行 |

## 构建方法

### 前置条件

- JDK 17 或更高版本
- Android SDK（仅用于构建 Android 兼容版本）

### 桌面测试构建

```bash
gradlew jar
```

构建完成后，模组 jar 文件位于 `build/libs` 目录。此版本仅适用于桌面测试，不能在 Android 上运行。

### GitHub Actions 构建（推荐）

项目已配置 GitHub Actions CI，每次提交都会自动构建。

1. 将项目推送到 GitHub 仓库
2. 进入仓库的 **Actions** 标签页
3. 选择最新的提交记录
4. 在 **Artifacts** 部分下载构建产物（为 zip 压缩包）
5. 解压后导入 Mindustry，此版本同时支持 Android 和桌面

### 本地全平台构建

1. 下载 Android SDK 并设置 `ANDROID_HOME` 环境变量
2. 安装 API level 30 和任意版本的 build tools（如 30.0.1）
3. 将 build-tools 目录添加到 PATH
4. 运行构建命令：

```bash
gradlew deploy
```

构建完成后，`build/libs` 目录下的 jar 文件可在 Android 和桌面端运行。

## 项目结构

```
Liminal-Protocol/
├── assets/                    # 资源文件
│   ├── bundles/               # 本地化文本
│   ├── maps/                  # 游戏地图
│   ├── schematics/            # 蓝图
│   ├── scripts/               # 脚本文件（已不启用）
│   ├── sounds/                # 音效
│   └── sprites/               # 贴图
│       ├── blocks/            # 方块贴图
│       ├── effects/           # 特效贴图
│       ├── items/             # 物品贴图
│       ├── units/             # 单位贴图
│       └── ui/                # UI 元素
├── content/                   # HJSON 配置文件（已不启用）
├── src/
│   └── LP/                    # Java 源代码
│       ├── content/           # 内容注册
│       │   ├── LPBlocks.java  # 方块注册
│       │   ├── LPItems.java   # 物品注册
│       │   ├── LPUnits.java   # 单位注册
│       │   └── LPFx.java      # 特效注册
│       ├── entities/          # 实体定义
│       │   ├── blocks/        # 方块逻辑
│       │   ├── bullets/       # 子弹类型
│       │   └── units/         # 单位逻辑
│       ├── graphics/          # 图形渲染
│       │   ├── Drawn.java     # 自定义绘制工具
│       │   └── PositionLightning.java # 位置闪电系统
│       ├── planet/            # 行星生成器
│       ├── ui/                # UI 组件
│       ├── util/              # 工具类
│       └── LPMod.java         # 模组入口
└── build.gradle               # Gradle 构建配置
```

## 主要特性

### 方块系统
- **AnnihilationReactor**：高级发电厂，风险与收益并存
- **MultiCrafter**：多配方工厂，灵活的生产系统
- **SquareRangePowerNode**：方形范围的电力节点

### 子弹系统
- **LightningLinkBulletType**：闪电链子弹，可创造闪电链持续对多个目标造成伤害
- **GravitationLightningLinkBulletType**：引力子弹，可吸引或排斥单位
- **EnergyBulletType**：能量子弹，无视甲伤害
- **等更多子弹类型**

### 图形系统
- **PositionLightning**：基于位置的闪电生成系统
- **Drawn**：自定义三角形和特效绘制工具
- **TrailEffect**：轨迹特效系统

## 添加依赖

所有 Mindustry、Arc 及其子模块的依赖必须在 Gradle 中声明为 `compileOnly`：

```groovy
compileOnly "com.github.Anuken:Mindustry:v158.1"
```

只有当你需要打包其他 Java 库且该库不在 Mindustry 中时，才使用 `implementation`。

---

# QQ群
+ **[Proxima Centauri](https://github.com/128OTEML/ProximaCentauri) / Liminal Protocol Mod交流群: 1085707299**

+ **一个鱼龙混杂的群，不建议加入: 198958025**

# 贡献者
+ **[128OTEML](https://github.com/128OTEML): 技术指导**
+ **[Martian238](https://github.com/Martian238): 原Json内容启发**
+ **[laomoze](https://github.com/laomoze/WarHammer): 贴图及特效启发**

--- 
[![Ask DeepWiki](https://deepwiki.com/badge.svg)](https://deepwiki.com/AEvoRlum/Liminal-Protocol)