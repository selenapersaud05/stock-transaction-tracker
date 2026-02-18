/**
 * StockLot represents one "lot" of stock shares that were bought at a certain price.
 * Each lot keeps track of how many shares it has and the buy price per share.
 */
public class StockLot {

    private int shares;       // number of shares in this lot
    private double buyPrice;  // purchase price per share

    /**
     * Creates a new stock lot with a number of shares and a buy price.
     */
    public StockLot(int shares, double buyPrice) {
        this.shares = shares;
        this.buyPrice = buyPrice;
    }

    /**
     * Gets how many shares are currently in this lot.
     */
    public int getShares() {
        return shares;
    }

    /**
     * Gets the buy price per share for this lot.
     */
    public double getBuyPrice() {
        return buyPrice;
    }

    /**
     * Updates the number of shares in this lot (used after a partial sale).
     */
    public void setShares(int shares) {
        this.shares = shares;
    }
}
