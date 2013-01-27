/Scenario File alpha format/

[filename]	(nato2)		

[uiname] 	(Nato 2)

[faction]	(1)

[tltip]		(Scramble Defense)

[desc]		([MSG]PRIORITY ALERT
[PAYLOAD] SkyPost Grant is under attack. We need you on point 
for defense. Reinforce the local garrison and protect the SkyPost.)

/scenario-specific things/
[levelType]	(scen)

/gun descriptors here/	
[plrGuns]{
	[item0]	(20mm)
	[item1]	(60mm)
	[item2] (las)
}

/motor descriptors here/
[plrMotors]{
	[item0]	(medEngine)
	[item1]	(largeEngine)
}

/ship descriptors here/
[plrShips]{
	[item0]	(gemini)
	[item1]	(lunar)
}

[music]	(systemWide)

[spawnX]	(0)
[spawnY]	(0)

[active]{
	[type] (circle)
	[x](0)[y](0)
	[radius](10000)
}

[margin]{
	[type] (circle)
	[x](0)[y](0)
	[radius](14000)
}
	
/NavPoints/
[navpoints]<
	{
		[name]		(Gamma)
		[x]		(0)
		[y]		(0)
		[state]		(t)
	}
>

/objectives/
[objectives]<
	{
		[name]	(ProtectSkyPost)
		[tltip]	(Go to Nav Gamma to reinforce SkyPost Grant)
		[desc]	(Two mercury class fighters are already on station. We need you to help
with the defense.)
		[target]()
		[state]	(true)	
	}
>

/Triggers/
[triggers]<
	/		INI		/
		/	IWin	/
		{
			[type]	(iwin)
			[name]	(thewin)
			[trigtype]	(TRIGGER)
			[x](0) [y](0)
			[collider]{
				[type] (circle)
				[x](0) [y](0)
				[radius](1)
			}
			[target]	()
			[trigstate]	(false)
			[state]	(1)
		}
		/	ILose	/
			/skypost blows up/
		{
			[type]	(iwin)
			[name]	(theloss)
			[trigtype]	(TRIGGER)
			[x](0) [y] (0)
			[collider]{
				[type]	(circle)
				[x](0) [y] (0)
				[radius](1)
			}
			[target]	()
			[trigstate]	(false)
			[state]	(-3)
		}
		/	Objective Counter	/
			/3 objectives, counter of 3/
		{
			[type]	(count)
			[name]	(objcounter)
			[trigtype]	(TRIGGER)
			[x](0) [y] (0)
			[collider]{
				[type] (circle)
				[x](0)[y](0)
				[radius](1)
			}
			[target]	()
			[trigstate]	(false)
			[total]	(3)
		}
		/	Player Spawn	/
			/make skypost/
		{
			[type]	(spawn)
			[name]	(makeSkylab)
			[trigtype]	(SHIP)
			[x](0) [y] (0)
			[collider]{
				[type] (circle)
				[x](0) [y](0)
				[radius] (1)
			}
			[target] (wv1spwn)
			[trigstate]	(false)
			[toSpawn]{
				[kind]	(skylab)
				[gun]	()
				[engine]()
				[loc]	(0 0)
				[isAi]	(f)
				[deathtrig](theloss)
			}
		}
		/		Wave 1		/
		/wave counter, tracks number of kills, fires wave 2/
		/counter/
		{
			[type]	(count)
			[name]	(wv1counter)
			[trigtype]	(TRIGGER)
			[x](0) [y] (0)
			[collider]{
				[type] (circle)
				[x](0)[y](0)
				[radius](1)
			}
			[target]	(wv2spwn)
			[trigstate]	(false)
			[total]	(2)
		}
		/multrigger spawning 2 voskhods, 2 allied mercurys/
		{
			[type]	(multrig)
			[name]	(wv1spwn)
			[trigtype]	(TRIGGER)
			[x](0) [y] (0)
			[collider]{
				[type]	(circle)
				[x](0) [y] (0)
				[radius](1)
			}
			[target]	()
			[trigstate]	(false)
			[targets]{
				[targ0] (spwnmerc1)
				[targ1] (spwnvskhd1)
				[targ2] (spwnvskhd2)
			}
		}
		/spawn vosk, point to wave counter/
		{
			[type]	(spawn)
			[name]	(spwnmerc1)
			[trigtype]	(TRIGGER)
			[x](0) [y] (0)
			[collider]{
				[type] (circle)
				[x](0) [y](0)
				[radius] (1)
			}
			[target] ()
			[trigstate]	(false)
			[toSpawn]{
				[kind]	(mercury)
				[gun]	(20mm)
				[engine](smallEngine)
				[loc]	(24 -10)
				[isAi]	(t)
				[deathtrig]()
			}
		}
		{
			[type]	(spawn)
			[name]	(spwnvskhd1)
			[trigtype]	(TRIGGER)
			[x](0) [y] (0)
			[collider]{
				[type] (circle)
				[x](0) [y](0)
				[radius] (1)
			}
			[target] ()
			[trigstate]	(false)
			[toSpawn]{
				[kind]	(voskhod)
				[gun]	(plas)
				[engine](rd109)
				[loc]	(-5000 0)
				[isAi]	(t)
				[deathtrig](wv1counter)
			}
		}
		{
			[type]	(spawn)
			[name]	(spwnvskhd2)
			[trigtype]	(TRIGGER)
			[x](0) [y] (0)
			[collider]{
				[type] (circle)
				[x](0) [y](0)
				[radius] (1)
			}
			[target] ()
			[trigstate]	(false)
			[toSpawn]{
				[kind]	(voskhod)
				[gun]	(plas)
				[engine](rd109)
				[loc]	(-5000 500)
				[isAi]	(t)
				[deathtrig](wv1counter)
			}
		}
	/		Wave 2		/
		/wave counter, tracks number of kills, fires wave 2/
		/counter/
		{
			[type]	(count)
			[name]	(wv2counter)
			[trigtype]	(TRIGGER)
			[x](0) [y] (0)
			[collider]{
				[type] (circle)
				[x](0)[y](0)
				[radius](1)
			}
			[target]	(wv3spwn)
			[trigstate]	(false)
			[total]	(3)
		}
		/multrigger spawning 2 voskhods, 2 allied mercurys/
		{
			[type]	(multrig)
			[name]	(wv2spwn)
			[trigtype]	(TRIGGER)
			[x](0) [y] (0)
			[collider]{
				[type]	(circle)
				[x](0) [y] (0)
				[radius](1)
			}
			[target]	()
			[trigstate]	(false)
			[targets]{
				[targ0] (spwnvskhd3)
				[targ1] (spwnvskhd4)
				[targ2] (spwnvstk1)
				[targ3]	(objcounter)
			}
		}
		{
			[type]	(spawn)
			[name]	(spwnvskhd3)
			[trigtype]	(TRIGGER)
			[x](0) [y] (0)
			[collider]{
				[type] (circle)
				[x](0) [y](0)
				[radius] (1)
			}
			[target] ()
			[trigstate]	(false)
			[toSpawn]{
				[kind]	(voskhod)
				[gun]	(plas)
				[engine](rd109)
				[loc]	(-1500 -4000)
				[isAi]	(t)
				[deathtrig](wv2counter)
			}
		}
		{
			[type]	(spawn)
			[name]	(spwnvskhd4)
			[trigtype]	(TRIGGER)
			[x](0) [y] (0)
			[collider]{
				[type] (circle)
				[x](0) [y](0)
				[radius] (1)
			}
			[target] ()
			[trigstate]	(false)
			[toSpawn]{
				[kind]	(voskhod)
				[gun]	(plas)
				[engine](rd109)
				[loc]	(1500 -4000)
				[isAi]	(t)
				[deathtrig](wv2counter)
			}
		}
		{
			[type]	(spawn)
			[name]	(spwnvstk1)
			[trigtype]	(TRIGGER)
			[x](0) [y] (0)
			[collider]{
				[type] (circle)
				[x](0) [y](0)
				[radius] (1)
			}
			[target] ()
			[trigstate]	(false)
			[toSpawn]{
				[kind]	(vostok)
				[gun]	(mcrwv)
				[engine](rd109)
				[loc]	(0 -4500)
				[isAi]	(t)
				[deathtrig](wv2counter)
			}
		}
	/		Wave 3		/
	/wave counter, tracks number of kills, fires wave 2/
		/counter/
		{
			[type]	(count)
			[name]	(wv3counter)
			[trigtype]	(TRIGGER)
			[x](0) [y] (0)
			[collider]{
				[type] (circle)
				[x](0)[y](0)
				[radius](1)
			}
			[target]	(objcounter)
			[trigstate]	(false)
			[total]	(4)
		}
		/multrigger spawning 2 voskhods, 2 allied mercurys/
		{
			[type]	(multrig)
			[name]	(wv3spwn)
			[trigtype]	(TRIGGER)
			[x](0) [y] (0)
			[collider]{
				[type]	(circle)
				[x](0) [y] (0)
				[radius](1)
			}
			[target]	()
			[trigstate]	(false)
			[targets]{
				[targ0] (spwnvstk3)
				[targ1] (spwnvstk4)
				[targ2] (spwnznd1)
				[targ3] (spwnznd2)
				[targ4]	(objcounter)
			}
		}
		{
			[type]	(spawn)
			[name]	(spwnvstk3)
			[trigtype]	(TRIGGER)
			[x](0) [y] (0)
			[collider]{
				[type] (circle)
				[x](0) [y](0)
				[radius] (1)
			}
			[target] ()
			[trigstate]	(false)
			[toSpawn]{
				[kind]	(vostok)
				[gun]	(mcrwv)
				[engine](rd109)
				[loc]	(0 -4500)
				[isAi]	(t)
				[deathtrig](wv3counter)
			}
		}
		{
			[type]	(spawn)
			[name]	(spwnvstk4)
			[trigtype]	(TRIGGER)
			[x](0) [y] (0)
			[collider]{
				[type] (circle)
				[x](0) [y](0)
				[radius] (1)
			}
			[target] ()
			[trigstate]	(false)
			[toSpawn]{
				[kind]	(vostok)
				[gun]	(mcrwv)
				[engine](rd109)
				[loc]	(0 4500)
				[isAi]	(t)
				[deathtrig](wv3counter)
			}
		}
		{
			[type]	(spawn)
			[name]	(spwnznd1)
			[trigtype]	(TRIGGER)
			[x](0) [y] (0)
			[collider]{
				[type] (circle)
				[x](0) [y](0)
				[radius] (1)
			}
			[target] ()
			[trigstate]	(false)
			[toSpawn]{
				[kind]	(zond4)
				[gun]	(105mm)
				[engine](ang191)
				[loc]	(4500 0)
				[isAi]	(t)
				[deathtrig](wv3counter)
			}
		}
		{
			[type]	(spawn)
			[name]	(spwnznd2)
			[trigtype]	(TRIGGER)
			[x](0) [y] (0)
			[collider]{
				[type] (circle)
				[x](0) [y](0)
				[radius] (1)
			}
			[target] ()
			[trigstate]	(false)
			[toSpawn]{
				[kind]	(zond4)
				[gun]	(105mm)
				[engine](ang191)
				[loc]	(4500 0)
				[isAi]	(t)
				[deathtrig](wv3counter)
			}
		}
>