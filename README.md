# Symulacja windy

Prosta aplikacja, pozwalająca przeprowadzić symulację systemu wind.
Z poziomu CLI użytkownik może zdefiniować budynek, ilość wind oraz początkowe oraz docelowe położenie agentów symulacyjnych.
Aplikacja umożliwia także monitorowanie kroków symulacji.

## Uruchomienie

### Konfiguracja

Po uruchomieniu programu wyświetla się menu główne, umożliwiające wydawanie poleceń aplikacji. Podczas pierwszego uruchomienia programu aplikacja pozwala tylko na utworzenie budynku, na którym będzie wykonywana symulacja. Istnieje możliwość późniejszego utworzenia innego budynku w trakcie działania programu.

Menu podczas pierwszego uruchomienia wygląda następująco:

```cmd
Select an option!
1 - create a building
0 - exit
```

Wybierając opcję `1` uruchamiany jest kreator budynku, który pozwala na wybranie ilości pięter piwnicznych, pięter naziemnych oraz wind. Należy pamiętać, że parter jest również piętren naziemnym.

```cmd
> 1
Basement floors number:  2
Floors number:  11
Elevators number:   2

Building created!
```

W następnej kolejności wyświetla się już menu właściwe programu:

```cmd
Select an option!
1 - create a building
2 - create agent
3 - generate random agents
4 - remove agents
5 - print building schema
6 - run simulation
0 - exit
```

Po utworzeniu budynku użytkownik może przejść do kreatora agentów symulacyjnych. Można tego dokonać, wybierając cyfrę `2`. Po wprowadzeniu parametrów zostanie wyświetlony komunikat potwierdzający utworzenie, jak w przypadku kreatora budynku.

```cmd
> 2
Starting floor:  7
Target floor:  0

Agent created!
```

Istnieje również możliwość wygenerowania losowo agentów, którzy mają zamiar poruszenia się w losowym kierunku. Menu uruchamia się wybierając cyfrę `3`, należy wówczas wprowadzić liczbę agentów do wygenerowania. Następnie zostaje wyświetlona lista agentów, któży zostali dodani do systemu.

Wybierając cyfrę `5`, można zobaczyć aktualny stan symulacji oraz rozmieszczenie agentów. Natomiast, wybierając cyfrę `4`, można usunąć agentów, jeżeli wprowadzono błędne dane.

```cmd
> 5
  0   0   0   0   0   0   1   0   0   2   0   0   1  | Agent count
--------[ 0]---------------------------------------- | 
--------[ 0]---------------------------------------- | 
 -2  -1   0   1   2   3   4   5   6   7   8   9  10  | Level
```
### Przebieg symulacji

Po wybraniu cyfry `6`, użytkownik ma możliwość uruchomienia symulacji. Może także wskazać, ile maksymalnie iteracji symulator ma wykonać oraz z jakim opóźnieniem. Wybranie odpowieniej wartości opóźnienia pozwala zwizualizować ruch windy w konsoli, aczkolwiek poprzednie ruchy zostają również zapisane na poczet możliwości późniejszej analizy ruchu wind. Kreator, który wyświetla się przed uruchomieniem symulacji wygląda następująco:

```cmd
> 6
Max simulation iterations:  500
Simulation delay [ms]:  500
```

Widok symulacji składa się z trzech elementów, z którego każdy jest prezentowany w jednej linijce:
- Liczba agentów na każdym z pięter;
- Wizualizacja szybów windy (każda linijka reprezentuje osobny szyb);
- Rozpis numerów poziomów.

Winda w ramach szybu reprezentowana jest przez liczbę, zamkniętą w nawiasie (`[ 0]`). Wartość tej liczby wskazuje aktualną liczbę agentów w niej przebywających. Winda posiada stan zamknięty oraz otwarty, który odnosi się do rzeczywistego stanu otwarcia drzwi windy. W symulacji, stan ten reprezentowany jest poprzez nawiasy kwadratowe (stan zamknięty `[ 0]`), bądź poprzez znak kreski pionowej (stan otwarty `| 0|`). Agenci przemieszczają się między windą a piętrem, tylko i wyłącznie kiedy stan otwarcia windy jest otwarty.

Po prawej stronie względem szybów windowych, wyświetlaja się lista kolejki wezwań windy. Kolejka ta wskazuje wszyskie wezwania dla danej windy, które reprezentowane są przez wartość liczbową piętra wezwania oraz kierunek (np. `7, DOWN;` to 7 piętro, zadeklarowana jazda w dół). Kolejka windy jest kreowana automatycznie przez agentów, którzy - podobnie jak w rzeczywistości - wzywają na danym piętrze windę deklarując jazdę w dół albo w górę, a w przypadku kiedy znajdują się w windzie - deklarują piętro na które chcą się dostać.

Opisując przebieg przykładowej symulacji, można dostrzec pewne opóźnienia w działaniu. Symulują one czas potrzebny na nacisięcie przez agentów przycisków oraz czas reakcji systemu na wezanie. Wobec tego ruch windy można zaobserwować dopiero od trzeciej iteracji. Ważnym aspektem jest również fakt, że system zdecydował się wykorzystać tylko jedną windę.

```cmd
  0   0   0   0   0   0   1   0   0   2   0   0   1  | Agent count
--------[ 0]---------------------------------------- | 
--------[ 0]---------------------------------------- | 
 -2  -1   0   1   2   3   4   5   6   7   8   9  10  | Level


  0   0   0   0   0   0   1   0   0   2   0   0   1  | Agent count
--------[ 0]---------------------------------------- | 
--------[ 0]---------------------------------------- | 7, DOWN; 4, DOWN; 10, DOWN; 
 -2  -1   0   1   2   3   4   5   6   7   8   9  10  | Level


  0   0   0   0   0   0   1   0   0   2   0   0   1  | Agent count
--------[ 0]---------------------------------------- | 
------------[ 0]------------------------------------ | 7, DOWN; 4, DOWN; 10, DOWN; 
 -2  -1   0   1   2   3   4   5   6   7   8   9  10  | Level
```

Po dotarciu windy do najwyższego piętra z kolejki, winda się otwiera i kontynuuje bieg do następnego wezwania poniżej. Do kolejki wezwań zostaje dodane kolejne piętro (wybrane przez agenta który wsiadł).

```cmd
  0   0   0   0   0   0   1   0   0   2   0   0   1  | Agent count
--------[ 0]---------------------------------------- | 
------------------------------------------------[ 0] | 7, DOWN; 4, DOWN; 10, DOWN; 
 -2  -1   0   1   2   3   4   5   6   7   8   9  10  | Level


  0   0   0   0   0   0   1   0   0   2   0   0   0  | Agent count
--------[ 0]---------------------------------------- | 
------------------------------------------------| 1| | 7, DOWN; 4, DOWN; 2, DOWN; 
 -2  -1   0   1   2   3   4   5   6   7   8   9  10  | Level


  0   0   0   0   0   0   1   0   0   2   0   0   0  | Agent count
--------[ 0]---------------------------------------- | 
--------------------------------------------[ 1]---- | 7, DOWN; 4, DOWN; 2, DOWN; 
 -2  -1   0   1   2   3   4   5   6   7   8   9  10  | Level


  0   0   0   0   0   0   1   0   0   2   0   0   0  | Agent count
--------[ 0]---------------------------------------- | 
----------------------------------------[ 1]-------- | 7, DOWN; 4, DOWN; 2, DOWN; 
 -2  -1   0   1   2   3   4   5   6   7   8   9  10  | Level


  0   0   0   0   0   0   1   0   0   2   0   0   0  | Agent count
--------[ 0]---------------------------------------- | 
------------------------------------[ 1]------------ | 7, DOWN; 4, DOWN; 2, DOWN; 
 -2  -1   0   1   2   3   4   5   6   7   8   9  10  | Level


  0   0   0   0   0   0   1   0   0   0   0   0   0  | Agent count
--------[ 0]---------------------------------------- | 
------------------------------------| 3|------------ | 4, DOWN; 2, DOWN; 0, DOWN; -1, DOWN; 
 -2  -1   0   1   2   3   4   5   6   7   8   9  10  | Level


  0   0   0   0   0   0   1   0   0   0   0   0   0  | Agent count
--------[ 0]---------------------------------------- | 
--------------------------------[ 3]---------------- | 4, DOWN; 2, DOWN; 0, DOWN; -1, DOWN; 
 -2  -1   0   1   2   3   4   5   6   7   8   9  10  | Level
```

Ostatecznie po zakończeniu symulacji można zaobserwować aktualne położenie agentów, którzy dotarli do swoich celów. Symulacja się zatrzymuje i wyświetla raport, który prezentuje ile iteracji wymaga obsłużenie pasażerów w danym przypadku, co pozwala na ocenę wydajności systemu w różnych scenariuszach. Wyświetlana jest także wydajność symulacji, w iteracjach na sekundę po odliczeniu opóźnienia.

```cmd
  0   1   2   0   1   0   0   0   0   0   0   0   0  | Agent count
--------[ 0]---------------------------------------- | 
----[ 0]-------------------------------------------- | 
 -2  -1   0   1   2   3   4   5   6   7   8   9  10  | Level


29 updates
14 updates/s
Select an option!
```

## Algorytm

Algorytm niniejszego programu, od najprostszych rozwiązań typu *FCFS* różni się tym, że implementuje on rzeczywisty sposób funkcjonowania wind.
Windy przechowują w sobie kolejkę pięter, na które zostały one wezwane przez agentów. W każdym kolejnym kroku przemieszczenia winda sprawdza, czy w kolejce nie wprowadzono zmian, ustala najwyższe oraz najniższe piętro, na które została wezwana/pokierowana, a następnie wykonuje kolejny krok. Winda zatrzymuje się oraz wpuszcza wyłącznie agentów, którzy wybrali kierunek jazdy zgodny z kierunkiem jazdy windy, co skraca czas przebywania agentów w windzie oraz pozwala systemowi wybrać bardziej optymalną czasowo windę dla danego piętra oraz kierunku jazdy. Po osiągnięciu najwyższego bądź najniższego piętra z kolejki, winda zmienia kierunek jazdy bądź kończy swój bieg, czekając na kolejne wezwanie. W ten sposób czas oczekiwania na windę jest znacznie skrócony, w porównaniu z algorytmem typu *FCFS*.

System obsługuje dowolną liczbę wind, w związku z czym musi również w jakiś sposób decydować, która spośród dostępnych będzie w danej sytuacji najlepsza. System najpierw upewnia się, które windy stoją bądź poruszają się w kierunku piętra, na które agent zgłosił wezwanie, następnie wybiera najbliższą temu piętru. W wypadku braku wytypowanej windy, system wybiera pierwszą dostępną windę która nie spełnia warunków.

Ważnym punktem algorytmu było rozdzielenie zadań na poszczególne klasy, tak aby odpowiadały one rzeczywistym zadaniom prawdziwych elementów takiego systemu. Tak więc `ElevatorSystem` wzywa `Elevator` za pośrednictwem reprezentanta klasy `Agent`. Całość znajduje się w `Building`, który podzielony jest na `Floor`. Zorganizowanie kodu w ten sposób wpływa pozytywnie na czytelność oraz skalowalność projektu.
