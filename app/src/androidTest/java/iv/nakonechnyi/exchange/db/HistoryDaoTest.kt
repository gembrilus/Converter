package iv.nakonechnyi.exchange.db

import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import androidx.test.platform.app.InstrumentationRegistry
import iv.nakonechnyi.exchange.generateConvertOperation
import iv.nakonechnyi.exchange.generateListOfConvertOperation
import iv.nakonechnyi.exchange.repeatProcedure
import kotlinx.coroutines.runBlocking
import org.hamcrest.MatcherAssert
import org.hamcrest.Matchers
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@SmallTest
@RunWith(AndroidJUnit4::class)
class HistoryDaoTest {

    private lateinit var db: HistoryDatabase
    private lateinit var dao: HistoryDao

    @Before
    fun before(){
        db = Room.inMemoryDatabaseBuilder(
            InstrumentationRegistry.getInstrumentation().targetContext,
            ConvertRoomDatabase::class.java
        ).build()
        dao = db.getDao()
    }

    fun after(){
        (db as RoomDatabase).close()
    }

    @Test
    fun whenInsertOneRecordThenReadTheSameOne() = runBlocking {

        val expected = generateConvertOperation()

        dao.saveOperation(expected)

        val actual = dao.getAllRecords()

        MatcherAssert.assertThat(actual, Matchers.allOf(Matchers.containsInAnyOrder(expected), Matchers.hasSize(1)))
    }

    @Test
    fun whenInsertManyRecordsThenReadTheSameAmountOfTheSameData() = runBlocking {

        val expected = generateListOfConvertOperation()

        repeatProcedure(expected, dao::saveOperation)

        val actual = dao.getAllRecords()

        MatcherAssert.assertThat(actual, Matchers.allOf(Matchers.containsInAnyOrder(*expected.toTypedArray()), Matchers.hasSize(expected.size)))
    }

    @Test
    fun whenInsertTheSameRecordsThenReadTheSameOne() = runBlocking {

        val item = generateConvertOperation()
        val expected = listOf(item, item, item)

        repeatProcedure(expected, dao::saveOperation)

        val actual = dao.getAllRecords()

        MatcherAssert.assertThat(actual, Matchers.allOf(Matchers.containsInAnyOrder(item), Matchers.hasSize(1)))
    }

}