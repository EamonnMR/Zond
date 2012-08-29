[1]{   [type](spawn)
	[ship]{
		[kind](mercury)
		[loc] (10 10)
		[engine](smallengine)
		[gun](20mm)
		[deatheffects]{[type](noop)}
		}
}
[2]{   [type](modtrig)
	[target](*a trigger*)
	[newstate](f)
}
[3]{   [type](navpoint)
	[target](*a navpoint*)
	[newstate](0)
}
[4]{   [type](objective)
	[target](*An objective*)
	[newstate](t)
	[newcompl](T)
}
[5]{   [type](action)
	[flags](ini)
	[done](FALSE)
}
[6]{   [type](action)
	[flags](fire)
	[done](true)
}
[7]{   [type](action)
	[flags](fire ini)
	[done](False)
}
[8]{   [type](action)
	[flags]()
	[done](True)
}
[9]{   [type](multi)
	[effects]<
		{[type](victory)}
		{[type](noop)}
		{[type](defeat)}
	>
}
>