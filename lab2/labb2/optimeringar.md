--Optimeringar

1.Implementera dynamiskprogrammering istället för rekursion:
    1. Möjligtvis skapa matris mer effektivt
    2. Basfall retunera direkt om längd = 0
    3.
    Innan:
        java Main -t
    Processing folder: ./test
    Processing testcase: testmedordlista
    CPU time for this test: 14 ms
    Processing testcase: testmedordlista2
    CPU time for this test: 1 ms
    Efter:
        java Main -t
        Processing folder: ./test
        Processing testcase: testmedordlista
        CPU time for this test: 10 ms
        Processing testcase: testmedordlista2
        CPU time for this test: 1 ms

2. Man kan utnyttja att ord kan ha prefix som redan har beräknats tidiagre och återanvända. 
    1. Utnyttja att indata är i bokstavsordning.
    2.
    3.
    Test Efter:
    java Main -t
    Processing folder: ./test
    Processing testcase: testmedordlista
    CPU time for this test: 5 ms
    Processing testcase: testmedordlista2
    CPU time for this test: 0 ms

    Large Efter:
    java Main -t large
    Processing folder: large
    Processing testcase: testmedordlista3
    CPU time for this test: 66 ms
    Processing testcase: testmedordlista4
    CPU time for this test: 55 ms
3. Terminera partDist tidigt om closestDistance överskrids
    1.
    2.
    3.
4. Filtrera bort onödiga ord att kolla på
    1.
    2.
    3.