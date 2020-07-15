package encrypto;

class Color {
    public static final int TOTAL_COLOR_BANDS = 4;
    int[] bands = new int[TOTAL_COLOR_BANDS];

    Color(int[] colorBands) throws IllegalArgumentException{
        if(colorBands.length != TOTAL_COLOR_BANDS)
            throw new IllegalArgumentException("length of colorBand doesn't match the supported band length: found:"+colorBands.length+", required: "+ TOTAL_COLOR_BANDS);
        this.bands = colorBands;
    }

    int getBand(int index) throws ArrayIndexOutOfBoundsException{
        return bands[index];
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("(");
        for (int b : bands) {
            sb.append(String.valueOf(b)).append(",");
        }
        sb.deleteCharAt(sb.length()-1);
        sb.append(")");

        return sb.toString();
    }
}