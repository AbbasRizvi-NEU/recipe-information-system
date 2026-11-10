public class UserInteraction {
    private String username;
    private String recipeName;
    private double rating;
    private boolean favorite;
    private String comment;

    // Constructor
    public UserInteraction(String username, String recipeName, double rating, boolean favorite, String comment) {
        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("Username cannot be null or empty");
        }
        if (recipeName == null || recipeName.trim().isEmpty()) {
            throw new IllegalArgumentException("Recipe name cannot be null or empty");
        }
        if (rating < 0.0 || rating > 5.0) {
            throw new IllegalArgumentException("Rating must be between 0.0 and 5.0");
        }
        this.username = username.trim();
        this.recipeName = recipeName.trim();
        this.rating = rating;
        this.favorite = favorite;
        this.comment = comment != null ? comment.trim() : "";
    }

    // Getters
    public String getUsername() {
        return username;
    }

    public String getRecipeName() {
        return recipeName;
    }

    public double getRating() {
        return rating;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public String getComment() {
        return comment;
    }

    // Setters
    public void setUsername(String username) {
        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("Username cannot be null or empty");
        }
        this.username = username.trim();
    }

    public void setRecipeName(String recipeName) {
        if (recipeName == null || recipeName.trim().isEmpty()) {
            throw new IllegalArgumentException("Recipe name cannot be null or empty");
        }
        this.recipeName = recipeName.trim();
    }

    public void setRating(double rating) {
        if (rating < 0.0 || rating > 5.0) {
            throw new IllegalArgumentException("Rating must be between 0.0 and 5.0");
        }
        this.rating = rating;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    public void setComment(String comment) {
        this.comment = comment != null ? comment.trim() : "";
    }

    public String toString() {
        String result = "User: " + username + "\n";
        result = result + "Rating: " + String.format("%.1f", rating) + "/5.0\n";
        result = result + "Favorite: " + (favorite ? "Yes" : "No") + "\n";
        result = result + "Comment: " + (comment.isEmpty() ? "None" : "\"" + comment + "\"");
        return result;
    }
}