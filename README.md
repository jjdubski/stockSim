<img width="800" height="374" alt="image" src="https://github.com/user-attachments/assets/6147bdbd-bb48-498b-9bcf-7bdac39d8989" />

# stockSim — Java Stock Market Simulation

A small, educational stock market simulation and library implemented in Java. The project models product order books, orders and quotes, matching/trading logic, a current-market publisher/subscriber system, and a simple GUI for observing market updates. It is intended for learning, experimentation, and lightweight simulation.

## Highlights

- Order book matching (price/time priority) implemented in `book` package
- `Price` and `PriceFactory` use integer cents to avoid floating-point issues
- `ProductManager` and `UserManager` provide high-level APIs and are singletons
- `TradingSim` runs an automated market simulation and hooks into the GUI
- A simple Swing-based GUI (in `gui`) shows current market/top-of-book updates

## Requirements

- JDK 8 or newer
- Basic shell tools (for compilation scripts) on macOS / Linux; equivalent commands work on Windows with minor tweaks

## Quick start — compile and run

From the repository root:

1. Create an output directory and compile all source files:

```bash
mkdir -p out
find src -name "*.java" > sources.txt
javac -d out @sources.txt
```

2. Run one of the example mains:

```bash
java -cp out Main    # runs the TradingSim simulation (includes GUI)
java -cp out Main2   # runs price/ProductBook demo (console)
java -cp out Main3   # runs ProductManager + user demo (console)
java -cp out Main4   # demo showing CurrentMarketPublisher + subscriptions
```

If you prefer an IDE: import this as a Java project (mark `src` as source root) and run any of the `Main` classes.

## What each `Main` does

- `Main` — Calls `sim.TradingSim.runSim()`, which sets up users/products, starts a `Gui`, subscribes to current-market events, and executes a loop that randomly adds/cancels orders to exercise matching logic.
- `Main2` — Demonstrates `Price` parsing and `ProductBook` behavior through console examples.
- `Main3` — Shows `ProductManager` features (adding products, quotes, trades) and inspects `User` state.
- `Main4` — Demonstrates `CurrentMarketPublisher` subscriptions and how the GUI and users receive market updates.

## Project structure (key packages)

- `src/book` — Core order-book logic:
  - `ProductBook.java` — top-level book for a single product; manages buy/sell sides and matching.
  - `ProductBookSide.java` — manages orders/quotes at specific prices and executes fills.
  - `ProductManager.java` — singleton that tracks all product books and provides product-level APIs.

- `src/tradable` — Domain objects:
  - `Order.java` — buy/sell orders with validation and DTO conversion.
  - `Quote.java`, `QuoteSide.java` — two-sided quotes composed of quote sides.
  - `Tradable.java` / `TradableDTO.java` — interfaces and lightweight DTOs used for user/state updates.

- `src/price` — Price handling:
  - `Price.java` — immutable price object storing cents as `int` and comparison helpers.
  - `PriceFactory.java` — parses strings/integers to `Price` and caches instances.

- `src/market` — Current-market publication:
  - `CurrentMarketPublisher.java`, `CurrentMarketTracker.java`, `CurrentMarketObserver.java` — publish/subscribe for top-of-book updates.

- `src/user` — User tracking:
  - `User.java` and `UserManager.java` — per-user state, subscribed markets, and tradable ownership.

- `src/gui` — Simple GUI for market display:
  - `Gui.java` — implements `CurrentMarketObserver` and updates `UserDisplayManager`.
  - `UserDisplayManager.java`, `MarketDisplay.java`, `MarketTableCellRenderer.java` — Swing UI components.

- `src/sim` — Simulation runner:
  - `TradingSim.java` — automated simulation that creates random orders/quotes and demonstrates matching.

- `src/exceptions` and package-specific exception classes — `DataValidationException`, `InvalidInput`, and `InvalidPriceOperation` used for validating inputs and operations.

- `trash/` — legacy/duplicate files. Not used by the build.

## How the matching works (high level)

- Orders are placed into a `ProductBook`, which maintains separate buy and sell `ProductBookSide` containers.
- Each side stores entries grouped by `Price` (a `HashMap<Price, ArrayList<Tradable>>`).
- `ProductBook.tryTrade()` repeatedly checks top-of-book buy and sell prices; if top buy >= top sell, it trades the minimum available quantity and records fills/cancellations.
- After changes, `CurrentMarketTracker` is updated and publishes to subscribers via `CurrentMarketPublisher`.

## Design notes and invariants

- `Price` is stored as an integer number of cents. Use `PriceFactory` to construct `Price` objects correctly and consistently.
- `ProductManager` and `UserManager` are singletons — use `getInstance()` to access them.
- `Order` and `Quote` constructors validate inputs (user IDs must be 3 letters, product symbols 1–5 letters, volumes within limits, and price non-null).

## Extending & debugging

- To add products or users programmatically, call `ProductManager.getInstance().addProduct(...)` or `UserManager.getInstance().init(...)`.
- The simulation parameters (loop count, sleep duration) are in `sim/TradingSim.java` — adjust the `for` loop or sleep interval to change runtime behavior.
- GUI updates are executed by the `Gui` and `UserDisplayManager`; `Gui.shutdown()` is called by the sim after completion.

## Common tasks

- Run a short simulation without GUI: modify `TradingSim` to avoid creating `Gui`, or run `Main2`/`Main3` for console-only demos.
- Change parsing/formatting of prices: edit `src/price/PriceFactory.java` and `src/price/Price.java`.

## Notes and caveats

- Some `Main` classes contain comments suggesting imports might need to be adjusted depending on how you import the project into an IDE — if you see compile errors, make sure `src` is a source root and that package structure is preserved.
- The `trash/` folder contains older copies — ignore it in builds.

## Where to look in the code

- Simulation entry: [src/sim/TradingSim.java](src/sim/TradingSim.java)
- Core book: [src/book/ProductBook.java](src/book/ProductBook.java)
- Book side behavior: [src/book/ProductBookSide.java](src/book/ProductBookSide.java)
- Order model: [src/tradable/Order.java](src/tradable/Order.java)
- Price handling: [src/price/Price.java](src/price/Price.java) and [src/price/PriceFactory.java](src/price/PriceFactory.java)
- GUI observer: [src/gui/Gui.java](src/gui/Gui.java)

---
