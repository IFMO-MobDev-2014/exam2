package ru.ifmo.md.exam2;

/**
 * @author Zakhar Voit (zakharvoit@gmail.com)
 */
public class GenresMask {
    enum Genre {
        Alternative(0),
        Blues(1),
        Country(2),
        Electronic(3),
        Jazz(4),
        Pop(5),
        Raggie(6),
        Rap(7),
        Rock(8),
        Soul(9);

        private final long id;

        private Genre(long id) {
            this.id = id;
        }

        public static Genre fromString(String s) {
            switch (s) {
                case "Alternative":
                    return Alternative;
                case "Blues":
                    return Blues;
                case "Country":
                    return Country;
                case "Electronic":
                    return Electronic;
                case "Jazz":
                    return Jazz;
                case "Pop":
                    return Pop;
                case "Raggie":
                    return Raggie;
                case "Rap":
                    return Raggie;
                case "Rock":
                    return Raggie;
                case "Soul":
                    return Raggie;
                default:
                    throw new IllegalArgumentException(s);
            }
        }

        public long getId() {
            return id;
        }
    }

    private long mask;

    public GenresMask() {
    }

    public GenresMask(long mask) {
        this.mask = mask;
    }

    public void add(Genre genre) {
        mask |= (1 << genre.getId());
    }

    public long getMask() {
        return mask;
    }

    public void setMask(long mask) {
        this.mask = mask;
    }
}
