package sk.tuke.kpi.oop.game;

//create enum Direction
public enum Direction {
    //set compass places in the world
    NORTH(0, 1), WEST(-1, 0), SOUTH(0, -1),  EAST(1, 0),
    NORTHWEST(-1, 1), NORTHEAST(1, 1), SOUTHWEST(-1, -1), SOUTHEAST(1, -1), NONE(0, 0);

    //create private final variables
    private final int dx;
    private final int dy;

    //create default Direction
    Direction(int dx, int dy) {
        //set variables
        this.dx = dx;
        this.dy = dy;
    }
    /*                      the compass angle
                                 N (360°)
                    NW (315°)                NE (45°)
           W (270°)                                      E (90°)
                    SW (225°)                SE (135°)
                                 S (180°)
    */

    //a method to get angle
    public float getAngle(){
        if(dx == 1){
            if(dy == 1) return 315.0f;
            else if(dy == 0) return 270.0f;
            else return 225.0f;
        } else if(dx == 0){
            /*if(dy == 1) return 360.0f;
            else if(dy == 0) return 0.0f;
            else return 180.0f;*/
            if(dy == 1) return 0;
            else return 180;
        } else{
            if(dy == 1) return 45.0f;
            else if(dy == 0) return 90.0f;
            else return 135.0f;
        }

        /*if(dx == 1){
            if(dy == 1) return 45.0f;
            else if(dy == 0) return 90.0f;
            else return 135.0f;
        } else if(dx == 0){
            if(dy == 1) return 360.0f;
            else if(dy == 0) return 0.0f;
            else return 180.0f;
        } else{
            if(dy == 1) return 315.0f;
            else if(dy == 0) return 270.0f;
            else return 225.0f;
        }*/
    }

    //a method, which returns direction in the world
    public static Direction fromAngle(float angle){
        if(angle == 0){
            return NORTH;
        } else if(angle == 45){
            return NORTHWEST;
        } else if(angle == 90){
            return WEST;
        } else if(angle == 135){
            return SOUTHWEST;
        } else if(angle == 180){
            return SOUTH;
        } else if(angle == 225){
            return SOUTHEAST;
        } else if(angle == 270){
            return EAST;
        } else{
            return NORTHEAST;
        }
    }

    //methods, which return position in dx and dy
    public int getDx(){
        return dx;
    }
    public int getDy(){
        return dy;
    }

    //a method, which combines two+ directions
    public Direction combine(Direction other){
        if(other == null) return null;
        if(this == other) return this;
        //created, because we can't change other.getX() and other.getY()
        int newX = other.getDx();
        int newY = other.getDy();

        Direction newDirection = NONE;
        if(/*other.getDx() + this.getDx() != 2*/ other.getDx() != this.getDx()) {
            //other.getX() += this.getX();
            newX += this.getDx();
        }
        if(/*other.getDy() + this.getDy() != 2*/ other.getDy() != this.getDy()) {
            //other.getY() += this.getY();
            newY += this.getDy();
        }
        for(Direction counter : Direction.values()){
            if(newX == counter.getDx() && newY == counter.getDy()){
                newDirection = counter;
            }
        }

        //return new direction (changed direction)
        return newDirection;
    }
}
