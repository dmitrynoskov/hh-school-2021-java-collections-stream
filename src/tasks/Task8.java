package tasks;

import common.Person;
import common.Task;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/*
А теперь о горьком
Всем придется читать код
А некоторым придется читать код, написанный мною
Сочувствую им
Спасите будущих жертв, и исправьте здесь все, что вам не по душе!
P.S. функции тут разные и рабочие (наверное), но вот их понятность и эффективность страдает (аж пришлось писать комменты)
P.P.S Здесь ваши правки желательно прокомментировать (можно на гитхабе в пулл реквесте)
 */
public class Task8 implements Task {

  private long count;

  //Не хотим выдывать апи нашу фальшивую персону, поэтому конвертим начиная со второй
  //ну со второй - так со второй, че бубнить то...
  //не хватает сигнатуры из плюсов (const List<Person>& persons), чтобы никто внутри метода не смел портить лист ремувами...
  public List<String> getNames(List<Person> persons) {
    return persons.stream()
            .skip(1)
            .map(Person::getFirstName)
            .toList();
  }

  //ну и различные имена тоже хочется
  //сет сам умеет делать дистинкт - это его инвариант
  public Set<String> getDifferentNames(List<Person> persons) {
    return new HashSet<>(getNames(persons));
  }

  //Для фронтов выдадим полное имя, а то сами не могут
  //в исходной версии было getSecondName()+getFirstName()+getSecondName() - опечатка? короче порядок из головы взял
  public String convertPersonToString(Person person) {
    return Stream.of(person.getSecondName(), person.getFirstName(), person.getMiddleName())
            .filter(Objects::nonNull)
            .collect(Collectors.joining(" "));
  }

  // словарь id персоны -> ее имя
  // должно и без дистинкта работать, просто в мапе будет перезаписывать одну и ту же пару ключ-значение,
  // но так мы убираем накладные расходы на лишние вызовы convertPersonToString с внутренним созданием стрима
  public Map<Integer, String> getPersonNames(Collection<Person> persons) {
    return persons.stream()
            .distinct()
            .collect(Collectors.toMap(Person::getId, this::convertPersonToString));
  }

  // есть ли совпадающие в двух коллекциях персоны?
  //тратим О(n) дополнительной памяти, чтобы по времени тратить не квадрат, а линию
  public boolean hasSamePersons(Collection<Person> persons1, Collection<Person> persons2) {
    Set<Person> personSet = new HashSet<>(persons1);
    return persons2.stream().anyMatch(personSet::contains);
  }

  //...
  public long countEven(Stream<Integer> numbers) {
    return numbers.filter(number -> number % 2 == 0).count();
  }

  @Override
  public boolean check() {
    System.out.println("Слабо дойти до сюда и исправить Fail этой таски?");
    boolean codeSmellsGood = ThreadLocalRandom.current().nextBoolean();
    boolean reviewerDrunk = (LocalDateTime.now().getDayOfWeek() == DayOfWeek.FRIDAY)
            && (LocalDateTime.now().getHour() >= 20);
    return codeSmellsGood || reviewerDrunk;
  }
}
