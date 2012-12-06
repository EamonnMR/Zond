[1]{ [type](spawn)
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
	[newstate](f)
}
[4]{   [type](objective)
	[target](*An objective*)
	[newstate](t)
	[newcompl](t)
}
[5]{   [type](action)
	[target](*An action*)
	[flags](ini)
	[done](f)
}
[6]{   [type](action)
	[target](*An action*)
	[flags](fire)
	[done](t)
}
[7]{   [type](action)
	[target](*An action*)
	[flags](fire ini)
	[done](f)
}
[8]{   [type](action)
	[target](*An action*)
	[flags]()
	[done](t)
}
[9]{   [type](multi)
	[effects]<
		{[type](victory)}
		{[type](noop)}
		{[type](defeat)}
	>
}
[10]{[type](victory)}
[11]		{[type](noop)}
[12]		{[type](defeat)}