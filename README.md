# Symulacja windy

Prosta aplikacja, pozwalająca przeprowadzić symulację systemu wind.
Z poziomu CLI użytkownik może zdefiniować budynek, ilość wind oraz początkowe oraz docelowe położenie agentów symulacyjnych.
Aplikacja umożliwia także monitorowanie kroków symulacji.

## Uruchomienie
Po uruchomieniu programu wyświetla się menu główne, umożliwiające wydawanie poleceń aplikacji. Wcześniej jednak aplikacja wymaga utworzenia budynku.

```
Select an option!
1 - create a building
2 - create agent
3 - remove agents
4 - print building schema
5 - run simulation
0 - exit
```

Po utworzeniu budynku użytkownik może przejść do kreatora agentów symulacyjnych. Można tego dokonać, wybierając cyfrę `2`. Po wprowadzeniu parametrów zostanie wyświetlony komunikat.

```
> 2
Starting floor:  7
Target floor:  0

Agent created!
```

Wybierając cyfrę `4`, można zobaczyć aktualny stan symulacji oraz rozmieszczenie agentów. Natomiast, wybierając cyfrę `3`, można usunąć agentów, jeżeli wprowadzono błędne dane.

```
> 4
  0   0   0   0   1   0   0   2   0   0 
[ 0]------------------------------------
[ 0]------------------------------------
```

Po wybraniu cyfry `5`, użytkownik ma możliwość uruchomienia symulacji. Może także wskazać, ile iteracji symulator ma wykonać oraz z jakim opóźnieniem.

```
> 5
Simulation iterations:  10
Simulation delay [ms]:  10
```

Następnie zostanie wyświetlony przebieg symulacji, krok po kroku.

```
  0   0   0   0   1   0   0   2   0   0 
[ 0]------------------------------------
[ 0]------------------------------------


  0   0   0   0   1   0   0   2   0   0 
[ 0]------------------------------------ 7, DOWN;  4, DOWN; 
[ 0]------------------------------------


  0   0   0   0   1   0   0   2   0   0 
----[ 0]-------------------------------- 7, DOWN;  4, DOWN; 
[ 0]------------------------------------


  0   0   0   0   1   0   0   2   0   0 
--------[ 0]---------------------------- 7, DOWN;  4, DOWN; 
[ 0]------------------------------------


  0   0   0   0   1   0   0   2   0   0 
------------[ 0]------------------------ 7, DOWN;  4, DOWN; 
[ 0]------------------------------------


  0   0   0   0   1   0   0   2   0   0 
----------------[ 0]-------------------- 7, DOWN;  4, DOWN; 
[ 0]------------------------------------


  0   0   0   0   1   0   0   2   0   0 
--------------------[ 0]---------------- 7, DOWN;  4, DOWN; 
[ 0]------------------------------------


...


  0   0   0   0   1   0   0   0   0   0 
----------------------------[ 2]-------- 4, DOWN;  0, DOWN; 
[ 0]------------------------------------

```

## Algorytm

Algorytm niniejszego programu, od najprostszych rozwiązań typu *FCFS* różni się tym, że implementuje on rzeczywisty sposób funkcjonowania wind.
Windy przechowują w sobie kolejkę pięter, na które zostały one wezwane przez agentów. W każdym kolejnym kroku przemieszczenia winda sprawdza, czy w kolejce nie wprowadzono zmian, ustala najwyższe oraz najniższe piętro, na które została wezwana/pokierowana, a następnie wykonuje kolejny krok. Winda zatrzymuje się oraz wpuszcza wyłącznie agentów, którzy wybrali kierunek jazdy zgodny z kierunkiem jazdy windy, co skraca czas przebywania agentów w windzie oraz pozwala systemowi wybrać bardziej optymalną czasowo windę dla danego piętra oraz kierunku jazdy. Po osiągnięciu najwyższego bądź najniższego piętra z kolejki, winda zmienia kierunek jazdy bądź kończy swój bieg, czekając na kolejne wezwanie. W ten sposób czas oczekiwania na windę jest znacznie skrócony, w porównaniu z algorytmem typu *FCFS*.

System obsługuje dowolną liczbę wind, w związku z czym musi również w jakiś sposób decydować, która spośród dostępnych będzie w danej sytuacji najlepsza. System najpierw upewnia się, które windy stoją bądź poruszają się w kierunku piętra, na które

agent zgłosił wezwanie, następnie wybiera najbliższą temu piętru.

Ważnym punktem algorytmu było rozdzielenie zadań na poszczególne klasy, tak aby odpowiadały one rzeczywistym zadaniom prawdziwych elementów takiego systemu. Tak więc `ElevatorSystem` wzywa `Elevator` za pośrednictwem reprezentanta klasy `Agent`. Całość znajduje się w `Building`, który podzielony jest na `Floor`. Zorganizowanie kodu w ten sposób wpływa pozytywnie na czytelność oraz skalowalność projektu.