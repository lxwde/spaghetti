$(() => {
    $('.list-item').each((i, listItem) => {
        // $(el).find('a.link-arrow') also works
        $('a.link-arrow', listItem).each((key, linkArrow) => {
            console.log(key + linkArrow);
            $(linkArrow).addClass('up');
            if ($(linkArrow).hasClass('link-current')) {
                $(linkArrow).addClass('active', 'down');
                $(linkArrow).next().attr('display', 'block');
            }

            $(linkArrow).on('click', (event) => {
                event.preventDefault();
                slideToggle($(linkArrow).next(), 200);
                $(linkArrow).add('transition');
                $(linkArrow).add('active');
                $(linkArrow).add('rotate');

                $(linkArrow).toggleClass('link-current');
            });
        });
    });

    function slideToggle(target, duration = 500) {
        if ($(target).css('display') === 'none') {
            return slideDown(target, duration);
        } else {
            return slideUp(target, duration);
        }
    }

    function slideUp(target, duration = 500) {
        $(target).attr('transitionProperty', 'height, margin, padding');
        $(target).attr();
    }

    function slideDown(target, duration = 500) {

    }
});