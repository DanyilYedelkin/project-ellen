package sk.tuke.kpi.oop.game;

public enum Direction {
    NORTH(0, 1), WEST(1, 0), SOUTH(0, -1),  EAST(-1, 0),
    NORTHWEST(1, 1), NORTHEAST(-1, 1), SOUTHWEST(1, -1), SOUTHEAST(-1, -1), NONE(0, 0);

    private final int dx;
    private final int dy;

    Direction(int dx, int dy) {
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

    public float getAngle(){
        if(dx == 1){
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
        }
    }
    public int getX(){
        return dx;
    }
    public int getY(){
        return dy;
    }

    public Direction combine(Direction other){
        if(other == null) return null;
        //created, because we can't change other.getX() and other.getY()
        int newX = other.getX();
        int newY = other.getY();

        Direction newDirection = NONE;
        if(other.getX() + this.getX() != 2) {
            //other.getX() += this.getX();
            newX += this.getX();
        }
        if(other.getY() + this.getY() != 2) {
            //other.getY() += this.getY();
            newY += this.getX();
        }
        for(Direction counter : Direction.values()){
            if(newX == counter.getX() && newY == counter.getY()){
                newDirection = counter;
            }
        }

        return newDirection;
    }
}
