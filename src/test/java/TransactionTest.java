import org.sparta.schedulemanagementapplicationserver.entity.Schedule;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.sparta.schedulemanagementapplicationserver.repository.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
public class TransactionTest {
//
//    @PersistenceContext
//    EntityManager em;
//
//    @Test
//    @Transactional
//    @Rollback(value = false)
//    @DisplayName("저장 성공")
//    void test1(){
//        Schedule schedule = new Schedule();
//        schedule.setTitle("제목");
//        schedule.setContents("내용");
//        schedule.setManager("ggumi");
//        schedule.setPassword("1234");
//
//        em.persist(schedule);
//    }
}
