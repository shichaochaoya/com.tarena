/**
 * 表示奖励，如果击中蜜蜂可以获得奖励
 * 两种方式
 *1.双倍火力2.增命
 */
public interface Award {
    int DOUBLE_FIRE=0;//双倍火力
    int LIFE=1;//一条命
   /*获得奖励类型(0\1)*/
    int getType();
}
