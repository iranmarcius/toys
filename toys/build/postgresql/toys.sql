create table locks (
	value varchar(100) not null,
	entity varchar(100) not null,
	owner varchar(100) not null,
	creation timestamp not null,
	expiration timestamp not null,
	primary key (value, entity)
)

drop function register_lock(varchar, varchar, varchar, time);
create or replace function register_lock(varchar, varchar, varchar, time) returns char as '
declare
	v alias for $1;
	e alias for $2;
	o alias for $3;
	dur alias for $4;
	cr timestamp;
	ex timestamp;
	st varchar;
	lck locks%ROWTYPE;
begin
	cr = localtimestamp;
	ex = cr + dur;
	
	select into lck * from locks where value=v and entity=e;
	if not found then
		insert into locks values (v, e, o, cr, ex);
		st = ''created'';
	else
		if lck.owner = o then
			update locks set creation=cr, expiration=ex where value=v and entity=e and owner=o;
			st = ''recycled'';
		else
			if lck.expiration <= localtimestamp then
				update locks set owner=o, creation=cr, expiration=ex where value=v and entity=e;
				st = ''stolen'';
			else
				raise exception ''O valor "%" da entidade "%" esta bloqueado para o proprietario "%"'', lck.value, lck.entity, lck.owner;
			end if;
		end if;
	end if;
	
	return
            st || ''\t'' ||
            v || ''\\t'' ||
            e || ''\\t'' ||
            o || ''\\t'' ||
            cr || ''\\t'' ||
            ex;
end;
' language 'plpgsql';

