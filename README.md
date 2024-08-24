# BlockPortals - Minecraft plugin

Allows admins to block and allows portal teleporting to overworld, nether, end

## Commands & Permissions

* `switchdim` - Switch dimension access. Permission - `blockportals.switchdim`. Alias - `switchdimension`
* `bpreload` - Reload BlockPortals. Permission - `blockportals.reload`. Aliases - `blockportalsreload`, `blockportalsrl`, `bprl`

## config.yml
```yaml
allow-end: true
allow-nether: true
allow-overworld: true

creative-bypass: true # players with creative game mode can bypass check and use portals as normal

messages:
  reloaded: '&aConfig reloaded!'
  allowed: '&aPortals to &e%dim%&a allowed'
  blocked: '&cPortals to &e%dim%&c blocked'
  allowed-already: '&aPortals to &e%dim%&d already&a allowed'
  blocked-already: '&cPortals to &e%dim%&d already&c blocked'
  incorrect-switch: '&cIncorrect world or state!'
```
