import java.awt.image.BufferedImage;

public abstract class FlyingObject {
    protected int x;//x坐标
    protected int y;//y坐标
    protected int width;//宽
    protected int height;//高
    protected BufferedImage image;//图片



    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHight() {
        return height;
    }

    public void setHight(int height) {
        this.height = height;
    }

    public BufferedImage getImage() {
        return image;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }

    /**飞行物移动一步*/
    public abstract void step();

    /**
     *检查当前飞行物是否被子弹击中
     *ture表示击中，飞行物可以被击中
     * @param bullet 子弹对象
     * @return true 表示被击中
     */
    public boolean shootBy(Bullet bullet){
        int x=bullet.x;
        int y=bullet.y;
        return this.x<x && x<this.x+width && this.y<y && y<this.y+height;
    }

    /**
     *检查是否出界
     * @param  width 边界宽
     * @param  height 边界高
     * @return ture出界与否
     */
    public abstract boolean outOfBounds();
}
