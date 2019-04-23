# Cypher

Malá aplikace na kódování a dekódování textu na základě klíčového slova a numerického posunu.

## Požadavky

Nainstalována Java 8+

## Instalace

Není třeba, spustitelný jar je součástí repa

## Použití

Jako zdroj vyberte existující textový soubor, cíl si můžete zvolit jakýkoliv, v případě neexistujícího souboru bude vytvořen, v případě existujícího bude přepsán. Pro kódování se používají pouze písmena české abecedy, kapitalizace je zachována.
Klíčové slovo je prostředek pro změnu pořadí písmen v abecedě, každý znak může být použit jednou.
V kombinaci s posunem se pak generuje finálová podoba abecedy. Pro znázornění je k dispozici tabulka s původní abecedou, po aplikaci klíčového slova a finální podoba po použití posunu.
Tlačítka "tam" a "zpět" určují operaci (kódování/dekódování), tedy při směru tam dojde k zakódování podle tabulky, pokud potom vyměníme soubory zdroj a cíl a stiskneme zpět, tak výsledkem je původní text.
