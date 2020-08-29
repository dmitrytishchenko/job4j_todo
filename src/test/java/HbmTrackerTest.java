import org.junit.Test;
import ru.job4j.hqldb.HbmTracker;
import ru.job4j.hqldb.Item;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class HbmTrackerTest {
    @Test
    public void whenCreate() {
        HbmTracker tracker = new HbmTracker();
        Item item = new Item("task1");
        tracker.create(item);
        List<Item> all = tracker.findAll();
        assertEquals(item, all.get(0));
    }

    @Test
    public void whenFindAll() {
        HbmTracker tracker = new HbmTracker();
        Item item1 = new Item("task1");
        Item item2 = new Item("task2");
        tracker.create(item1);
        tracker.create(item2);
        assertEquals(List.of(item1, item2), tracker.findAll());
    }

    @Test
    public void whenFindById() {
        HbmTracker tracker = new HbmTracker();
        Item item = new Item("task1");
        tracker.create(item);
        assertEquals(item, tracker.findById(1));
    }

    @Test
    public void whenDeleteById() {
        HbmTracker tracker = new HbmTracker();
        Item item = new Item("task1");
        tracker.create(item);
        tracker.delete(item.getId());
        assertEquals(0, tracker.findAll().size());
    }
}
