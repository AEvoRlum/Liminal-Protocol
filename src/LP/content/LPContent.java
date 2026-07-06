package LP.content;

import mindustry.ctype.Content;
import mindustry.ctype.ContentType;

public class LPContent extends Content{

    public LPContent(){
        super();
    }

    @Override
    public ContentType getContentType(){
        return ContentType.error;
    }

    @Override
    public void load(){
    }

    public void update(){
    }

    public void removed(){
    }

    public void init(){
        super.init();
    }

    public static void loadPriority(){
    }
}
