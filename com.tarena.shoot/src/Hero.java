import java.awt.image.BufferedImage;

/**
 * 英雄机
 */
public class Hero extends FlyingObject {
    protected BufferedImage[]images={};//表HERO的贴图，hero有两张图片组成
    protected int index=0;//使两张图片交替显示的计数

    private int doubleFire;
    private int life;

    /**
     * 飞机的构造方法，将属性初始化
     */
    public Hero(){
        life=3;
        doubleFire=0;
        this.image=ShootGame.hero0;
        images=new BufferedImage[]{ShootGame.hero0,ShootGame.hero1};
        width=image.getWidth();
        height=image.getHeight();
        x=150;
        y=400;
    }
    /** step方法，实现了图片更换，有动画效果*/
    @Override
    public void step(){
        if(images.length>0){
            image=images[index++/10%images.length];
        }
    }
    /**获取英雄机命数*/
    public int getLife(){
        return life;
    }

    /**发射子弹*/
    public Bullet[] shoot(){//发射子弹
        int xStep=width/4;
        int yStep=20;
        if(doubleFire>0){
            Bullet[] bullets=new Bullet[2];
            bullets[0]=new Bullet(x+xStep,y-yStep);
            bullets[1]=new Bullet(x+3*xStep,y-yStep);
            doubleFire -= 2;
            return bullets;
        }else {//单倍
            Bullet[] bullets=new Bullet[1];
            bullets[0]=new Bullet(x+2*xStep,y-yStep);//y-yStep（子弹到飞机的位置）
            return bullets;
        }
    }
/**鼠标移动方法*/
    public void moveTo(int x,int y){
    this.x=x-width/2;
    this.y=y-height/2;
}
    /**实现越界判断方法*/
    @Override
    public boolean outOfBounds(){
        return false;
    }
/**获取双倍火力*/
public void addDoubleFire(){
    doubleFire+=40;
}
public void addLife(){//增命
    life++;
}
public void subtractLife(){//减命
    life--;
}
public void setDoubleFire(int doubleFire){//重新设置双倍火力的值
    this.doubleFire=doubleFire;
}
public boolean hit(FlyingObject other){//碰撞算法
    int x1=other.x-this.width/2;
    int x2=other.x+other.width+this.width/2;
    int y1=other.y-this.height/2;
    int y2=other.y+other.height+this.height/2;
    return this.x+this.width/2>x1 && this.x+this.width/2<x2 && this.y+this.height/2>y1 && this.y+this.width/2<y2;
}

}
