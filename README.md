# Fixed Villager Trades Mod (Fabric)

"Fixed" in the title doesn't mean I'm fixing broken trades, however you can definitely interpret it that way. What it means 
is that Villager trades are now fixed the moment they get a profession. This does not prevent you from changing their profession,
only you won't be able to reroll their trades to get what you want. The villagers trades will remain the same as it was for 
each profession no matter how many times you replace their workstation.

Obviously the biggest thing affected by this is librarian villagers and the Mending Enchantment. Prior to 1.14, you couldn't
reroll professions to get your desired enchanted book. As a result, Mending, the most powerful enchantment in the game, was really hard to get.
With 1.14+, you can place and replace a lectern over and over until you get yourself a nice cheap Mending dispenser. This, 
in my opinion, is a large oversight from Mojang. This mod merely implements the simplest solution for those who want it.

This mod also limits the max discount a villager trade can have. The default is set to 50%.

## 🧰 Setup

This mod can function entirely serverside. It is not necessary to install it on the client. Servers this is installed on can have vanilla 
players join and experience the effects. It does not require the Fabric API, only the fabric loader.

## ⚙ Configuration

The two functions of this mod can be configured and used independently.

> `FixedTrades`
> > Whether villager trades are fixed to their profession upon generation. Must be either true or false. Default is true.

> `MaxDiscount`
> > The maximum discount a trade can have. Must be between 0.0 (No discount) and 1.0 (Vanilla behavior). Default is 0.5.

## 📜 License

Copyright 2022 Quplet, Apache License 2.0. Please credit if you use or distribute my work.
