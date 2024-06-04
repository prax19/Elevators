# Symulacja windy

Porsta aplikacja, pozwalająca na przeprowadzenie symulacji systemu wind.
Z poziomu CLI aplikacji, użytkownik ma możliwość zdefiniowania budynku, ilości wind a także początkowe oraz docelowe położenie agentów symulacyjnych.
Aplikacja umożliwia także monitorowanie kroków symulacj.

## Uruchomienie
Po uruchomieniu programu, wyświetla się menu główne, umożliwiające wydawanie poleceń aplikacji. Uprzednio aplikacja wymaga utworzenie budynku.

```
Select an option!
1 - create a building
2 - create agent
3 - remove agents
4 - print building schema
5 - run simulation
0 - exit
```

Po utworzeniu budynku, użytkownik może przejść do kreatora agentów symulacyjnych. Można tego dokonać wybierając cyfrę `2`. Po wprowadzeniu parametrów, zostanie wyświetlony komunikat.

```
> 2
Starting floor:  7
Target floor:  0

Agent created!
```

Wybierając cyfrę `4`, można zobaczyć aktualny stan symulacji oraz rozmieszczenie agentów. Wybierając cyfrę `3` można natomiast usunąć agentów, jeżeli wprowadzono błędne dane.

```
> 4
  0   0   0   0   1   0   0   2   0   0 
[ 0]------------------------------------
[ 0]------------------------------------
```

Po wybraniu cyfry `5`, użytkownik ma możliwość uruchomienia symulacji. Może także wskazać ile iteracji symulator ma wykonać oraz z jakim opóźnieniem.

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


  0   0   0   0   1   0   0   2   0   0 
------------------------[ 0]------------ 7, DOWN;  4, DOWN; 
[ 0]------------------------------------


  0   0   0   0   1   0   0   2   0   0 
----------------------------[ 0]-------- 7, DOWN;  4, DOWN; 
[ 0]------------------------------------


  0   0   0   0   1   0   0   2   0   0 
----------------------------[ 0]-------- 7, DOWN;  4, DOWN; 
[ 0]------------------------------------


  0   0   0   0   1   0   0   0   0   0 
----------------------------[ 2]-------- 4, DOWN;  0, DOWN; 
[ 0]------------------------------------
```
