<!---freshmark shields
links = [];
if ('{{curseForgeId}}' && '{{curseForgeSlug}}') {
  links.push(
    link(
      image('CurseForge', 'https://cf.way2muchnoise.eu/{{curseForgeId}}.svg'),
      'https://www.curseforge.com/minecraft/mc-mods/{{curseForgeSlug}}'
    ),
    link(
      image('CurseForge', 'https://cf.way2muchnoise.eu/versions/{{curseForgeId}}.svg'),
      'https://www.curseforge.com/minecraft/mc-mods/{{curseForgeSlug}}'
    )
  );
}
if ('{{modrinthId}}' && '{{modrinthSlug}}') {
  links.push(
    link(
      image('Modrinth', 'https://img.shields.io/modrinth/dt/{{modrinthId}}?label=Modrinth'),
      'https://modrinth.com/mod/{{modrinthSlug}}'
    )
  );
}
if ('{{githubRepo}}') {
  links.push(
    link(
      image('Github build status', 'https://github.com/{{githubRepo}}/actions/workflows/build.yml/badge.svg'),
      'https://github.com/{{githubRepo}}/actions/workflows/build.yml'
    )
  )
}
output = links.join('\n');
-->

[![CurseForge](https://cf.way2muchnoise.eu/317548.svg)](https://www.curseforge.com/minecraft/mc-mods/always-drop-loot)
[![CurseForge](https://cf.way2muchnoise.eu/versions/317548.svg)](https://www.curseforge.com/minecraft/mc-mods/always-drop-loot)
[![Github build status](https://github.com/sargunv/always-drop-loot/actions/workflows/build.yml/badge.svg)](https://github.com/sargunv/always-drop-loot/actions/workflows/build.yml)

<!---freshmark /shields -->

# Always Drop Loot

Make mobs always drop exp and loot, regardless of cause of death.
