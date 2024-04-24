package com.example.wordsfactory

import com.example.wordsfactory.common.Constants
import com.example.wordsfactory.data.database.MeaningDao
import com.example.wordsfactory.data.database.WordDao
import com.example.wordsfactory.data.model.Meaning
import com.example.wordsfactory.data.model.WordTable
import com.example.wordsfactory.data.repository.WordRepositoryImpl
import com.example.wordsfactory.domain.usecase.DecreaseWordCounterUseCase
import com.example.wordsfactory.domain.usecase.GetQuestionsUseCase
import com.example.wordsfactory.domain.usecase.IncreaseWordCounterUseCase
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

// todo тесты:
// 1. Проверить что все слова разные
// 2. Проверить что количество вопросв равно константе
// 3. Проверить что берутся слова с наименьшим коэффициентом
// 4. Проверить что коэффициент увеличивается при правильном ответе
// 5. Проверить что коэффициент уменьшается при неправильном ответе
class QuestionLogicTest {
    private val dispatcher = StandardTestDispatcher()
    private lateinit var getQuestionsUseCase: GetQuestionsUseCase

    @Mock
    private lateinit var decreaseWordCounterUseCase: DecreaseWordCounterUseCase

    @Mock
    private lateinit var increaseWordCounterUseCase: IncreaseWordCounterUseCase

    @Mock
    private lateinit var wordDao: WordDao

    @Mock
    private lateinit var meaningDao: MeaningDao


    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        getQuestionsUseCase = GetQuestionsUseCase(WordRepositoryImpl(wordDao, meaningDao))
        decreaseWordCounterUseCase =
            DecreaseWordCounterUseCase(WordRepositoryImpl(wordDao, meaningDao))
        increaseWordCounterUseCase =
            IncreaseWordCounterUseCase(WordRepositoryImpl(wordDao, meaningDao))
        Dispatchers.setMain(dispatcher)
    }

    @Test
    fun `test all words in answer are different`() = runTest {
        `when`(wordDao.getAll()).thenReturn(List(10) { WordTable(it, "Word$it", 0) })
        `when`(wordDao.getWorseWords(Constants.QUESTIONS_COUNT)).thenReturn(List(10) {
            WordTable(
                it,
                "Word$it",
                0
            )
        })
        `when`(meaningDao.getMeanings(anyInt())).thenReturn(
            listOf(
                Meaning(
                    wordId = 1,
                    meaning = "meaning1"
                ), Meaning(wordId = 2, meaning = "meaning2")
            )
        )
        val questions = getQuestionsUseCase.execute(Constants.QUESTIONS_COUNT)
        println(questions)
        questions.forEach { question ->
            question.answers.forEach { answer ->
                assertEquals(1, question.answers.count { it == answer })
            }
        }
    }

    @Test
    fun `test number of questions equals constant`() = runTest {
        `when`(wordDao.getAll()).thenReturn(List(10) { WordTable(it, "Word$it", 0) })
        `when`(wordDao.getWorseWords(Constants.QUESTIONS_COUNT)).thenReturn(List(10) {
            WordTable(
                it,
                "Word$it",
                0
            )
        })
        `when`(meaningDao.getMeanings(anyInt())).thenReturn(
            listOf(
                Meaning(
                    wordId = 1,
                    meaning = "meaning1"
                ), Meaning(wordId = 2, meaning = "meaning2")
            )
        )
        val questions = getQuestionsUseCase.execute(Constants.QUESTIONS_COUNT)
        assertEquals(Constants.QUESTIONS_COUNT, questions.size)
    }

    @Test
    fun `test words with lowest coefficient are chosen`() = runTest {
        val wordTable = List(30) { WordTable(it, "Word$it", it) }
        `when`(wordDao.getAll()).thenReturn(
            wordTable
        )
        `when`(wordDao.getWorseWords(Constants.QUESTIONS_COUNT)).thenReturn(
            wordTable.sortedBy { it.count }.take(Constants.QUESTIONS_COUNT)
        )
        `when`(meaningDao.getMeanings(anyInt())).thenReturn(
            listOf(
                Meaning(
                    wordId = 1,
                    meaning = "meaning1"
                ), Meaning(wordId = 2, meaning = "meaning2")
            )
        )
        val worseWords = wordDao.getWorseWords(Constants.QUESTIONS_COUNT)
        val questions = getQuestionsUseCase.execute(Constants.QUESTIONS_COUNT)
        assertEquals(
            worseWords.map { word -> word.name }.sorted(),
            questions.map { question -> question.answers.find { it.isCorrect }?.text }
                .sortedBy { it })
    }

    @Test
    fun `test coefficient increases with correct answer`() = runTest {
        val wordTable = List(30) { WordTable(it, "Word$it", it) }
        `when`(wordDao.getAll()).thenReturn(
            wordTable
        )
        `when`(wordDao.getWorseWords(Constants.QUESTIONS_COUNT)).thenReturn(
            wordTable.sortedBy { it.count }.take(Constants.QUESTIONS_COUNT)
        )
        `when`(meaningDao.getMeanings(anyInt())).thenReturn(
            listOf(
                Meaning(
                    wordId = 1,
                    meaning = "meaning1"
                ), Meaning(wordId = 2, meaning = "meaning2")
            )
        )
        `when`(
            increaseWordCounterUseCase.execute(
                anyString()
            )
        ).then { mock ->
            val word = wordTable.find { it.name == mock.arguments[0] }
            if (word != null) {
                word.count++
            }
        }
        val worseWords = wordDao.getWorseWords(Constants.QUESTIONS_COUNT)
        val questions = getQuestionsUseCase.execute(Constants.QUESTIONS_COUNT)
        val correctAnswer = questions.first().answers.find { it.isCorrect }?.text
        val correctWord = worseWords.find { it.name == correctAnswer }
        if (correctWord != null) {
            val startWordCount = correctWord.count
            increaseWordCounterUseCase.execute(correctWord.name)
            assertEquals(
                startWordCount + 1,
                wordDao.getAll().find { it.name == correctWord.name }?.count
            )
        }
    }

    @Test
    fun `test coefficient decreases with incorrect answer`() = runTest {
        val wordTable = List(30) { WordTable(it, "Word$it", it) }
        `when`(wordDao.getAll()).thenReturn(
            wordTable
        )
        `when`(wordDao.getWorseWords(Constants.QUESTIONS_COUNT)).thenReturn(
            wordTable.sortedBy { it.count }.take(Constants.QUESTIONS_COUNT)
        )
        `when`(meaningDao.getMeanings(anyInt())).thenReturn(
            listOf(
                Meaning(
                    wordId = 1,
                    meaning = "meaning1"
                ), Meaning(wordId = 2, meaning = "meaning2")
            )
        )
        `when`(
            decreaseWordCounterUseCase.execute(
                anyString()
            )
        ).then { mock ->
            val word = wordTable.find { it.name == mock.arguments[0] }
            if (word != null) {
                word.count--
            }
        }
        val worseWords = wordDao.getWorseWords(Constants.QUESTIONS_COUNT)
        val questions = getQuestionsUseCase.execute(Constants.QUESTIONS_COUNT)
        val incorrectAnswer = questions.first().answers.find { !it.isCorrect }?.text
        val incorrectWord = worseWords.find { it.name == incorrectAnswer }
        if (incorrectWord != null) {
            val startWordCount = incorrectWord.count
            decreaseWordCounterUseCase.execute(incorrectWord.name)
            assertEquals(
                startWordCount - 1,
                wordDao.getAll().find { it.name == incorrectWord.name }?.count
            )
        }
    }
}