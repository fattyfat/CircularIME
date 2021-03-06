package fatty.circularime;

import android.graphics.PointF;

/**
 * Created by Fatty on 2016-12-02.
 */

public class en_circularIME_view_right {

    private PointF posCircleCenter;                                                // Left circularIME's center point of touch
    private float firstCircleRadius, secendCircleRadius, thirdCircleRadius,
            forthCircleRadius, fifthCircleRadius;                    // Radius of circle

    final double amountRow = 0.0175;           /// pi/180 角度1度時的弧度

    private int swtichKeyboardCounter = 0;
    final private String[][] EN_right_keyboardArray = {
            {"63", "wxyz", "tuv", "pqrs", ""},
            {"44", "mno", "jkl", "ghi", "1#"},
            {"46", "def", "abc", "-1", "33"},
            {"-4", "32", "-5", "", ""}};
/*          ↑上方矩陣視覺化↓ 上下顛倒左右相反，不能直接按照[row][col]這樣直接一對一對應

            ｜ENTER｜空白鍵 ｜DEL  ｜      ｜     ｜
            ｜  !  ｜大小寫 ｜ABC  ｜DEF   ｜  .  ｜
            ｜ 12# ｜GHI   ｜JKL  ｜MNO   ｜  ,  ｜
            ｜     ｜PQRS  ｜TUV  ｜WXYZ  ｜  ?  ｜

            註1：12# 意思為 切數字/標點符號鍵盤。
            註2：沒有內容代表目前尚未設定功能。
*/
final private String[][] EN_right_numeral_keyboardArray = {   //數字鍵盤
        {"63", "57", "56", "55", "48"},
        {"44", "54", "53", "52", "1#"},
        {"46", "51", "50", "49", "33"},
        {"-4", "32", "-5", "", ""}};
    /*      ↑上方矩陣視覺化↓ 上下顛倒左右相反，不能直接按照[row][col]這樣直接一對一對應

                ｜ENTER｜空白鍵｜ DEL｜     ｜     ｜
                ｜  !  ｜  1  ｜  2 ｜  3  ｜  .  ｜
                ｜ 12# ｜  4  ｜  5 ｜  6  ｜  ,  ｜
                ｜  0  ｜  7  ｜  8 ｜  9  ｜  ?  ｜

                註1：12# 意思為 切數字/標點符號鍵盤。
                註2：沒有內容代表目前尚未設定功能。
    */
    final private String[][] EN_right_symbol_keyboardArray = {
            {"63", "35", "41", "40", "59"},
            {"44", "95", "45", "58", "1#"},
            {"46", "34", "39", "64", "33"},
            {"-4", "32", "-5", "", ""}};
    /*          ↑上方矩陣視覺化↓ 上下顛倒左右相反，不能直接按照[row][col]這樣直接一對一對應

                ｜ENTER｜空白鍵｜ DEL｜     ｜     ｜
                ｜  .  ｜  @  ｜  ' ｜  "  ｜  !  ｜
                ｜  ,  ｜  :  ｜  - ｜  _  ｜ 12# ｜
                ｜  ?  ｜  (  ｜  ) ｜  #  ｜  ;  ｜

                註1：12# 意思為 切數字/標點符號鍵盤。
                註2：沒有內容代表目前尚未設定功能。
    */
    private  String[][] keyboardArray = {   //初始值為字母鍵盤
            {"63", "wxyz", "tuv", "pqrs", ""},
            {"44", "mno", "jkl", "ghi", "1#"},
            {"46", "def", "abc", "-1", "33"},
            {"-4", "32", "-5", "", ""}};

    public en_circularIME_view_right() {

        posCircleCenter = null;                /// Current point of touch

        firstCircleRadius = 0;                /// Radius of circle
        secendCircleRadius = 0;                /// Radius of circle
        thirdCircleRadius = 0;                /// Radius of circle
        forthCircleRadius = 0;                /// Radius of circle
        fifthCircleRadius = 0;                /// Radius of circle
    }   /**初始化*/

    public void setCircularRadius_enRight(int screenWidth, int screenHeight, int view_width, int view_height) {

        firstCircleRadius = Math.round(0.3f * view_height);
        secendCircleRadius = Math.round(0.5f * view_height);
        thirdCircleRadius = Math.round(0.7f * view_height);
        forthCircleRadius = Math.round(0.9f * view_height);
        fifthCircleRadius = view_height;

        posCircleCenter = new PointF(screenWidth, screenHeight);    // Set center point 訂直屏右下角

        swtichKeyboardCounter = 0;
        System.arraycopy(EN_right_keyboardArray, 0, keyboardArray, 0, EN_right_keyboardArray.length);
    }   /**設定鍵盤座標資訊*/

    public String ACTION_DOWN_EVENT(PointF posTouchDown, boolean enableAD ,boolean adEvent){
    /**return #1:do nothing(在外圈無用地帶) #2:切換左右雙手輸入法 #3:切換英中日輸入法 other:output String*/
        double touchRadius = getRadius(posTouchDown);
        double touchAngle = getAngle(posTouchDown);

        if (touchRadius > fifthCircleRadius ) { //點擊座標半徑超過圖形，視為無效操作

            int radiusMultiple = (int) ((float) touchRadius / (fifthCircleRadius/600));
            int angleMultiple = (int) (touchAngle / amountRow);

            if ((700 <= radiusMultiple && radiusMultiple <= 800) && angleMultiple >= 83) {
                return "#2";
            }else if (870 <= radiusMultiple && (51 <= angleMultiple && angleMultiple <= 58  && enableAD)){
                return "#4";
            }else if ((760 <= radiusMultiple && radiusMultiple <= 850) && (61 <= angleMultiple && angleMultiple <= 64) && adEvent) {
                return "#5";
            }else if ((615 <= radiusMultiple && radiusMultiple <= 750) && (54 <= angleMultiple && angleMultiple <= 60) && adEvent) {
                return "#4";
            }
            return "#1";
        }
        else if(touchRadius < firstCircleRadius) {
            return "#3";
        } else {
            return getButtoned(posTouchDown);
        }
    }   /**回應按下的結果*/

    public int switchKeyboardArray()    /**更換鍵盤種類 字母鍵盤→數字鍵盤→標點符號鍵盤→loop */
    {
        swtichKeyboardCounter++;

        switch (swtichKeyboardCounter % 3){

            case 0: //字母鍵盤
                System.arraycopy(EN_right_keyboardArray, 0, keyboardArray, 0, EN_right_keyboardArray.length);
                return 0;
            case 1: //數字鍵盤
                System.arraycopy(EN_right_numeral_keyboardArray, 0, keyboardArray, 0, EN_right_numeral_keyboardArray.length);
                return 1;
            case 2: //標點符號鍵盤
                System.arraycopy(EN_right_symbol_keyboardArray, 0, keyboardArray, 0, EN_right_symbol_keyboardArray.length);
                return 2;
            default:
                return -1;
        }
    }

    private double getRadius(PointF p)  /**計算半徑*/
    {
        // Get difference of coordinates
        double x = p.x - posCircleCenter.x;
        double y = p.y - posCircleCenter.y;

        // Return distance calculated with Pythagoras
        return Math.sqrt(x * x + y * y);
    }

    //** Gets the angle of point p relative to the center 弧度*/
    private double getAngle(PointF p)   /**計算角度*/
    {
        // Get difference of coordinates
        double x = p.x - posCircleCenter.x;
        double y = p.y - posCircleCenter.y;

        // Calculate angle with special atan (calculates the correct angle in all quadrants)
        double angle = Math.atan2(y, x);

        return Math.abs(angle) - Math.PI/2;
    }

    private String getButtoned(PointF posTouchDown)     /**計算該座標所代表的按鈕*/
    {

        double Radius = getRadius(posTouchDown);
        double Angle = getAngle(posTouchDown);

        int whichCycle = 0;
        int whichDirection = 0;

        //找這座標在第幾圈
        //以圓的1/600作為除數，計算半徑有六百分之一半徑的幾倍來計算在哪一圈
        //小於180 第一圈(最內圈) 中英日切換輸入法，但由於在input_onTouchUp內已經篩選過，所以這裡不會出現
        //180<= X <300 第一圈  (最內圈)
        //300<= X <420 第二圈
        //420>= X <540 第三圈
        //540>= X <600 第四圈 (最外圈)
        int radiusMultiple = (int) ((float) Radius / (fifthCircleRadius / 600));

        if (radiusMultiple >= 540)
            whichCycle = 3;
        else if (radiusMultiple >= 420)
            whichCycle = 2;
        else if (radiusMultiple >= 300)
            whichCycle = 1;
        else if (radiusMultiple >= 180)
            whichCycle = 0;

        int angleMultiple = (int) (Angle / amountRow);              //找這角度在哪一個column

        if (whichCycle == 3) {            //如果在最外圈

            if (angleMultiple <= 30)
                whichDirection = 0;       //小於等於30度，設whichDirection為0。 (最外圈DEL鍵)
            else if (angleMultiple <= 60)
                whichDirection = 1;       //大於30，小於等於60度，設whichDirection為1。 (最外圈空白鍵)
            else
                whichDirection = 2;       //大於60，小魚等於90度，設whichDirection為2。 (最外圈ENTER鍵)

        } else {                          //內圈4圈

            if (angleMultiple <= 15)
                whichDirection = 0;       //小於等於15度，設whichDirection為0。(NULL；12#)
            else if (angleMultiple <= 35)
                whichDirection = 1;       //大於15，小於等於35度，設whichDirection為1。(WXYZ；MNO；DEF)
            else if (angleMultiple <= 55)
                whichDirection = 2;       //大於35，小於等於55度，設whichDirection為2。(TUV；JKL；ABC)
            else if (angleMultiple <= 75)
                whichDirection = 3;       //大於55，小於等於75度，設whichDirection為3。(PQRS；GHI；↑)
            else
                whichDirection = 4;       //大於75，小於等於90度，設whichDirection為4。(?；,；.)
        }

        return keyboardArray[whichCycle][whichDirection];
    }
}
