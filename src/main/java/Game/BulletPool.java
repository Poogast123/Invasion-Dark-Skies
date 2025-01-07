package Game;

import java.util.LinkedList;

public class BulletPool {
    private final LinkedList<Bullet> pool = new LinkedList<>();

    // Get a bullet from the pool, or create a new one if the pool is empty
    public Bullet getBullet(double startX, double startY) {
        if (pool.isEmpty()) {
            return new Bullet(startX, startY); // No bullets available, create new
        }
        Bullet bullet = pool.removeFirst(); // Reuse an existing bullet
        bullet.setLayoutX(startX);
        bullet.setLayoutY(startY);
        return bullet;
    }

    // Return a bullet to the pool
    public void returnBullet(Bullet bullet) {
        pool.add(bullet); // Return bullet to the pool
    }

    // Return all active bullets in the pool as an array
    public Bullet[] getAllActiveBullets() {
        return pool.toArray(new Bullet[0]); // Convert the LinkedList to an array and return
    }

    // Clear all bullets from the pool
    public void clear() {
        pool.clear(); // Clears the entire LinkedList of bullets
    }
}
