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
        $(target).prop('transitionProperty', 'height, margin, padding');
        $(target).prop('transitionDuration', duration + 'ms');
        $(target).prop('boxSizing', 'border-box');
        $(target).prop('height', target.offsetHeight + 'px');
        $(target).prop('overflow', 'hidden');
        $(target).prop('height', 0);
        $(target).prop('paddingTop', 0);
        $(target).prop('paddingBottom', 0);
        $(target).prop('marginTop', 0);
        $(target).prop('marginBottom', 0);
        setTimeout(() => {
            $(target).prop('display', 'none');
            $(target).removeProp('height', 'padding-top', 'padding-bottom',
                'margin-top', 'margin-bottom', 'overflow', 
                'transition-duration', 'transition-property');
        }, duration);

    }

    function slideDown(target, duration = 500) {
        $(target).removeProp('display');
        if ($(target).prop('display') == 'none') {
            $(target).prop('display', 'block')
        }

        $(target).prop('overflow', 'hidden');
        $(target).prop('height', 0);
        $(target).prop('paddingTop', 0);
        $(target).prop('paddingBottom', 0);
        $(target).prop('marginTop', 0);
        $(target).prop('marginBottom', 0);
        $(target).prop('boxSizing', 'border-box');
        $(target).prop('transitionProperty', 'height, margin, padding');
        $(target).prop('transitionDuration', duration + 'ms');
        $(target).prop('height', target.offsetHeight + 'px');
        
        $(target).removeProp('padding-top', 'padding-bottom', 'margin-top', 'margin-bottom');
        
        setTimeout(() => {
            $(target).removeProp('height', 'overflow', 'transition-duration', 'transition-property');
        }, duration);

    }
});