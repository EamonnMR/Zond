/Scenario File alpha format/

[filename]	(soviet1)		

[uiname] 	(Soviet 1)		/name of the scenario, used as the name in the storage system/	

[faction]	(0)			/USSR or NATO?/

[tltip]		(Area Denial)

[desc]		(ATTN: Senior Cyborg Lieutenant Yegorova
SR: Adm. Pavlovitch, KGB
This is a mission of the utmost secrecy. In order to secure vital 
asteroids, we need you to:)

/scenario-specific things/
[levelType]	(scen) /as opposed to camp for campaign/

/gun descriptors here/	
[plrGuns]{
	[item0]	(plas)
	[item1]	(mcrwv)
}

/motor descriptors here/
[plrMotors]{
	[item0]	(rd109)
	[item1]	(ang191)
	[item2]	(7kl3)
}

/ship descriptors here/
[plrShips]{
	[item0]	(voskhod)
	[item1]	(vostok)
}

[music]	(loneRecon)

[spawnX]	(0)
[spawnY]	(0)

[active]{
	[type] (circle)
	[x](0)[y](0)
	[radius](9000)
}

[margin]{
	[type] (circle)
	[x](0)[y](0)
	[radius](10000)
}
	
/NavPoints/
[navpoints]<
	{
		[name]		(Kappa)
		[x]		(1000)
		[y]		(-4000)
		[state]		(t)
	}
	{
		[name]		(Iama)
		[x]		(-4500)
		[y]		(4500)
		[state]		(t)
	}
	{
		[name]		(Omnek)
		[x]		(4500)
		[y]		(5000)
		[state]		(t)
	}
	{
		[name]		(Center)
		[x]		(0)
		[y]		(0)
		[state]		(t)
	}
>

/objectives/
[objectives]<
	{
		[name]	(Baddies)
		[tltip]	(Make sure no enemy ships remain.)
		[desc]	(Enemy will call for reinforcements, destroy them.)
		[target]()
		[state]	(false)	
	}
	{
		[name]	(Satellite3)
		[tltip]	(Destroy Satellite at Omnek)
		[desc]	(Knockout satellite in orbit at point Omnek)
		[target]()
		[state]	(false)	
	}
	{
		[name]	(Satellite2)
		[tltip]	(Destroy Satellite at Iama)
		[desc]	(Knockout satellite in orbit at point Iama)
		[target]()
		[state]	(false)	
	}
	{
		[name]	(Satellite1)
		[tltip]	(Destroy Satellite at Kappa)
		[desc]	(Knockout satellite in orbit at point Kappa)
		[target]()
		[state]	(false)	
	}

>

/Triggers/
[triggers]<
	/iwin/
	{
		[type]		(iwin)
		[name]		(thewin)
		[trigtype]	(TRIGGER)
		[x]		(96000)
		[y]		(96000)
		[collider]{
			[type] (circle)
			[x](96000)[y](96000)
			[radius](1)
		}
		[target]	()
		[trigstate]	(f)
		[state]		(1)
	}

	/counter/
	{	
		[type]	(count)
		[name]	(objcounter)
		[trigtype](TRIGGER)
		[x]	(-1000)
		[y]	(1000)
		[collider]{
			[type] (circle)
			[x](0)[y](0)
			[radius](1)
		}
		[target]	(iWin)
		[trigstate]	(false)
		[total]	(4)
	}
	/satellite spawns/
	{	
		[type]	(multrig)
		[name]	(makeSats)
		[trigtype](SHIP)
		[x]	(-1000)
		[y]	(1000)
		[collider]{
			[type] (circle)
			[x](0)[y](0)
			[radius](100)
		}
		[target]	()
		[trigstate]	(false)
		[targets]{
			[targ0](mariner)
			[targ1](pionr)
			[targ2](voyag)
		}
	}
	/mariner/
	{
		[type]	(spawn)
		[name]	(mariner)
		[trigtype](TRIGGER)
		[x](10000)
		[y](10000)
		[collider]{
			[type] (circle)
			[x](10000)[y](10000)
			[radius](1)
		}
		[target]	()
		[trigstate]	(f)
		[toSpawn]{
			[kind] (marinSat)
			[gun]	()
			[engine](satEngine)
			[loc](950 -4000)
			[isAi](t)
			[deathtrig](togkappa)
		}
	
	}
	/pioneer/
	{
		[type]	(spawn)
		[name]	(pionr)
		[trigtype](TRIGGER)
		[x](100)
		[y](100)
		[collider]{
			[type] (circle)
			[x](10000)[y](10050)
			[radius](1)
		}
		[target]	()
		[trigstate]	(false)
		[toSpawn]{
			[kind] (pionrGun)
			[gun]	(las)
			[engine](satEngine)
			[loc](-4500 5050)
			[isAi](t)
			[deathtrig](togiama)
		}
	
	}
	/voyager/
	{
		[type]	(spawn)
		[name]	(voyag)
		[trigtype](TRIGGER)
		[x](-100)
		[y](-100)
		[collider]{
			[type] (circle)
			[x](10000)[y](10100)
			[radius](1)
		}
		[target]	()
		[trigstate]	(false)
		[toSpawn]{
			[kind] (voyagSat)
			[gun]	(las)
			[engine](satEngine)
			[loc](4450 5090)
			[isAi](t)
			[deathtrig](makeShips)
		}
	
	}
	/toggle navs/
	{
		[type]	(togglenav)
		[name]	(togkappa)
		[trigtype](TRIGGER)
		[x](-3000)
		[y](-3000)
		[collider]{			
			[type] (circle)
			[x](10000)[y](10150)
			[radius](1)
		}
		[target]	(compKappa)
		[trigstate]	(false)
		[navPointName]	(Kappa)
		[setstate]	(f)
		
	}
	{
		[type]	(togglenav)
		[name]	(togiama)
		[trigtype](TRIGGER)
		[x](-6000)
		[y](-3000)
		[collider]{			
			[type] (circle)
			[x](10000)[y](10200)
			[radius](1)
		}
		[target]	(compIama)
		[trigstate]	(false)
		[navPointName]	(Iama)
		[setstate]	(f)
		
	}
	{
		[type]	(togglenav)
		[name]	(togomnek)
		[trigtype](TRIGGER)
		[x](-3000)
		[y](-7000)
		[collider]{			
			[type] (circle)
			[x](10000)[y](10250)
			[radius](1)
		}
		[target]	(compOmnek)
		[trigstate]	(false)
		[navPointName]	(Omnek)
		[setstate]	(f)
		
	}

	/tog objectives/
	{
		[type]	(endobj)
		[name]	(compKappa)
		[trigtype](TRIGGER)
		[x](-3000)
		[y](-7000)
		[collider]{			
			[type] (circle)
			[x](10000)[y](10300)
			[radius](1)
		}
		[target]	(objcounter)
		[trigstate]	(false)
		[objective](Satellite1)
		
	}
	{
		[type]	(endobj)
		[name]	(compIama)
		[trigtype](TRIGGER)
		[x](-3000)
		[y](-7000)
		[collider]{			
			[type] (circle)
			[x](10000)[y](10350)
			[radius](1)
		}
		[target]	(objcounter)
		[trigstate]	(false)
		[objective](Satellite2)
		
	}
	{
		[type]	(endobj)
		[name]	(compOmnek)
		[trigtype](TRIGGER)
		[x](-3000)
		[y](-7000)
		[collider]{			
			[type] (circle)
			[x](10000)[y](10400)
			[radius](1)
		}
		[target]	(objcounter)
		[trigstate]	(false)
		[objective](Satellite3)
		
	}

	{
		[type]	(endobj)
		[name]	(compBad)
		[trigtype](TRIGGER)
		[x](-3000)
		[y](-7000)
		[collider]{			
			[type] (circle)
			[x](10000)[y](10450)
			[radius](1)
		}
		[target]	(objcounter)
		[trigstate]	(false)
		[objective](Baddies)
		
	}

	/spawn ships/
	/counter/
	{	
		[type]	(count)
		[name]	(shipcounter)
		[trigtype](TRIGGER)
		[x]	(-1000)
		[y]	(1000)
		[collider]{
			[type] (circle)
			[x](10000)[y](10500)
			[radius](1)
		}
		[target]	(compBad)
		[trigstate]	(false)
		[total]	(3)
	}

	{	
		[type]	(multrig)
		[name]	(makeShips)
		[trigtype](SHIP)
		[x]	(-1000)
		[y]	(1000)
		[collider]{
			[type] (circle)
			[x](10000)[y](10550)
			[radius](100)
		}
		[target]	(togomnek)
		[trigstate]	(false)
		[targets]{
			[targ0](merc1)
			[targ1](merc2)
			[targ2](gem1)
		}
	}

	/merc1/
	{
		[type]	(spawn)
		[name]	(merc1)
		[trigtype](TRIGGER)
		[x](-100)
		[y](-100)
		[collider]{
			[type] (circle)
			[x](10000)[y](10600)
			[radius](1)
		}
		[target]	()
		[trigstate]	(false)
		[toSpawn]{
			[kind] (mercury)
			[gun]	(20mm)
			[engine](smallEngine)
			[loc](4500 5400)
			[isAi](t)
			[deathtrig](shipcounter)
		}
	
	}
	/merc2/
	{
		[type]	(spawn)
		[name]	(merc2)
		[trigtype](TRIGGER)
		[x](-100)
		[y](-100)
		[collider]{
			[type] (circle)
			[x](10000)[y](10650)
			[radius](1)
		}
		[target]	()
		[trigstate]	(false)
		[toSpawn]{
			[kind] (mercury)
			[gun]	(20mm)
			[engine](smallEngine)
			[loc](4500 5200)
			[isAi](t)
			[deathtrig](shipcounter)
		}
	
	}
	/gemini/
	{
		[type]	(spawn)
		[name]	(gem1)
		[trigtype](TRIGGER)
		[x](-100)
		[y](-100)
		[collider]{
			[type] (circle)
			[x](10000)[y](10700)
			[radius](1)
		}
		[target]	()
		[trigstate]	(false)
		[toSpawn]{
			[kind] (gemini)
			[gun]	(60mm)
			[engine](medEngine)
			[loc](4500 5100)
			[isAi](t)
			[deathtrig](shipcounter)
		}
	
	}
>